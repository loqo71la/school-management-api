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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
public class DeleteClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestUtil testUtil;

    private MockMvc mockMvc;

    private UUID clazzId;

    private UUID studentId;

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
    public void DeleteClazzEndpoint_WithValidClazzCode_ReturnsSuccess() throws Exception {
        // Arrange
        var student = testUtil.buildStudent(studentId, "00001", "Jose", "Perez");
        student.setClazzList(new ArrayList<>(List.of(testUtil.buildClazz(clazzId, "3A-189", "Geology", "Sedimentary Petrology"))));

        when(clazzRepository.existsByIdAndCreatedBy(clazzId, "test")).thenReturn(true);
        when(studentRepository.findAllByClazz(clazzId)).thenReturn(List.of(student));

        // Act
        ResultActions result = mockMvc.perform(delete(String.format("/api/clazz/%s", clazzId))
                .header("Authorization", testUtil.buildToken()));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is(String.format("Clazz with ID [%s] was successfully removed.", clazzId))));

        verify(clazzRepository).existsByIdAndCreatedBy(clazzId, "test");
        verify(clazzRepository).deleteById(clazzId);
        verify(studentRepository).findAllByClazz(clazzId);
        verify(studentRepository).saveAll(List.of(student));
    }

    @Test
    public void DeleteClazzEndpoint_WithInvalidClazzCode_ReturnsError() throws Exception {
        // Arrange
        when(studentRepository.findAllByClazz(clazzId)).thenReturn(List.of());

        // Act
        ResultActions result = mockMvc.perform(delete(String.format("/api/clazz/%s", clazzId))
                .header("Authorization", testUtil.buildToken()));

        // Assert
        result.andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("You don’t have permission to remove this Clazz on this server.")));

        verify(clazzRepository).existsByIdAndCreatedBy(clazzId, "test");
        verify(clazzRepository, never()).deleteById(clazzId);
        verify(studentRepository, never()).findAllByClazz(clazzId);
        verify(studentRepository, never()).saveAll(List.of());
    }
}
