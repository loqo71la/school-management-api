package dev.loqo71la.schoolmanagement.student;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import dev.loqo71la.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteStudentTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestUtil testUtil;

    private MockMvc mockMvc;

    private UUID studentId;

    private UUID clazzId;

    @BeforeEach
    public void setup() {
        studentId = UUID.randomUUID();
        clazzId = UUID.randomUUID();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void DeleteStudentEndpoint_WithValidStudentId_ReturnsSuccess() throws Exception {
        // Arrange
        var clazz = testUtil.buildClazz(clazzId, "1A-192", "Geology", "Sedimentary Petrology");
        clazz.setStudentList(new ArrayList<>(List.of(testUtil.buildStudent(studentId, "00001", "Jose", "Perez"))));

        when(studentRepository.existsByIdAndCreatedBy(studentId, "test")).thenReturn(true);
        when(clazzRepository.findAllByStudent(studentId)).thenReturn(List.of(clazz));

        // Act
        ResultActions result = mockMvc.perform(delete(String.format("/api/student/%s", studentId))
                .header("Authorization", testUtil.buildToken()));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is(String.format("Student with ID [%s] was successfully removed.", studentId))));

        assertTrue(clazz.getStudentList().isEmpty());
        assertEquals(0, clazz.getTotalAssigned());

        verify(studentRepository).existsByIdAndCreatedBy(studentId, "test");
        verify(studentRepository).deleteById(studentId);
        verify(clazzRepository).findAllByStudent(studentId);
        verify(clazzRepository).saveAll(List.of(clazz));
    }

    @Test
    public void DeleteStudentEndpoint_WithInvalidId_ReturnsError() throws Exception {
        // Arrange
        when(clazzRepository.findAllByStudent(studentId)).thenReturn(List.of());

        // Act
        ResultActions result = mockMvc.perform(delete(String.format("/api/student/%s", studentId))
                .header("Authorization", testUtil.buildToken()));

        // Assert
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("You don’t have permission to remove this Student on this server.")));

        verify(studentRepository).existsByIdAndCreatedBy(studentId, "test");
        verify(clazzRepository, never()).findAllByStudent(studentId);
        verify(clazzRepository, never()).saveAll(List.of());
        verify(studentRepository, never()).deleteById(studentId);
    }
}
