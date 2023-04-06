package dev.loqo71la.schoolmanagement.clazz;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
import dev.loqo71la.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostAssignStudentTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestUtil testUtil;

    private MockMvc mockMvc;

    private UUID teacherId;

    private UUID clazzId;

    @BeforeEach
    public void setup() {
        teacherId = UUID.randomUUID();
        clazzId = UUID.randomUUID();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void PostAssignStudentEndpoint_WithoutPayload_ReturnsSuccess() throws Exception {
        // Arrange
        var clazz = testUtil.buildClazz(clazzId, "1A-192", "Geology", "Sedimentary Petrology");
        when(clazzRepository.findById(clazzId)).thenReturn(Optional.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(post(String.format("/api/clazz/%s/assign", clazzId))
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"teacherId\": \"%s\", \"studentIds\": []}", teacherId)));

        // Assert
        result.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("The assigned teacher must be part of the clazz.")));

        verify(clazzRepository).findById(clazzId);
        verify(studentRepository, never()).findAllByIds(anyList());
        verify(clazzRepository, never()).save(clazz);
    }

    @Test
    public void PostAssignStudentEndpoint_WithInvalidStudentId_ReturnsNotFoundError() throws Exception {
        // Arrange
        var johnId = UUID.randomUUID();
        var clazz = testUtil.buildClazz(clazzId, "1A-192", "Geology", "Sedimentary Petrology");

        when(clazzRepository.findById(clazzId)).thenReturn(Optional.of(clazz));
        when(studentRepository.findAllByIds(List.of(johnId, teacherId))).thenReturn(List.of());

        // Act
        ResultActions result = mockMvc.perform(post(String.format("/api/clazz/%s/assign", clazzId))
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"teacherId\": \"%s\", \"studentIds\": [\"%s\", \"%s\"]}", teacherId, johnId, teacherId)));

        // Assert
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is(String.format("Clazz with ID [%s, %s] was not found.", johnId, teacherId))));

        verify(clazzRepository).findById(clazzId);
        verify(studentRepository).findAllByIds(List.of(johnId, teacherId));
        verify(clazzRepository, never()).save(clazz);
    }

    @Test
    public void PostAssignStudentEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        var johnId = UUID.randomUUID();
        var clazz = testUtil.buildClazz(clazzId, "1A-192", "Geology", "Sedimentary Petrology");
        var students = List.of(testUtil.buildStudent(johnId, "0001", "John", "Tester"),
                testUtil.buildStudent(teacherId, "0002", "Mark", "Tester"));

        when(clazzRepository.findById(clazzId)).thenReturn(Optional.of(clazz));
        when(studentRepository.findAllByIds(List.of(johnId, teacherId))).thenReturn(students);

        // Act
        ResultActions result = mockMvc.perform(post(String.format("/api/clazz/%s/assign", clazzId))
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"teacherId\": \"%s\", \"studentIds\": [\"%s\", \"%s\"]}", teacherId, johnId, teacherId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is(String.format("Students were successfully assigned to Clazz with ID [%s].", clazzId))));

        verify(clazzRepository).findById(clazzId);
        verify(studentRepository).findAllByIds(List.of());
        verify(clazzRepository).save(clazz);
    }
}
