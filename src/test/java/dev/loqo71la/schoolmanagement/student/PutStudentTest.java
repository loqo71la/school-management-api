package dev.loqo71la.schoolmanagement.student;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
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

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PutStudentTest {

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestUtil testUtil;

    private MockMvc mockMvc;

    private UUID studentId;

    @BeforeEach
    public void setup() {
        studentId = UUID.randomUUID();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void PutStudentEndpoint_WithValidPayload_ReturnsSuccess() throws Exception {
        // Arrange
        when(studentRepository.findById(studentId))
                .thenReturn(Optional.of(testUtil.buildStudent(studentId, "00001", "Jose", "Perez")));

        // Act
        ResultActions result = mockMvc.perform(put(String.format("/api/student/%s", studentId))
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jose\", \"lastName\": \"Perez\"}"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is(String.format("Student with ID [%s] was successfully updated.", studentId))));

        verify(studentRepository).findById(studentId);
        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void PutStudentEndpoint_WithInvalidId_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(studentRepository.findById(studentId))
                .thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(put(String.format("/api/student/%s", studentId))
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jose\", \"lastName\": \"Perez\"}"));

        // Assert
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is(String.format("Student with ID [%s] was not found.", studentId))));

        verify(studentRepository).findById(studentId);
        verify(studentRepository, never()).save(any(Student.class));
    }
}
