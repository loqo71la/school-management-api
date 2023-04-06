package dev.loqo71la.schoolmanagement.clazz;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
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

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetByIdClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MockMvc mockMvc;

    private UUID clazzId;

    @BeforeEach
    public void setup() {
        clazzId = UUID.randomUUID();
    }

    @Test
    public void GetClazzByCodeEndpoint_WithValidClazzCode_ReturnsAClazz() throws Exception {
        // Arrange
        when(clazzRepository.findById(clazzId))
                .thenReturn(Optional.of(testUtil.buildClazz(clazzId, "1A-192", "Geology", "Sedimentary Petrology")));

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s", clazzId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.code", is("1A-192")))
                .andExpect(jsonPath("$.title", is("Geology")))
                .andExpect(jsonPath("$.description", is("Sedimentary Petrology")));

        verify(clazzRepository).findById(clazzId);
    }

    @Test
    public void GetClazzByCodeEndpoint_WithInvalidClazzCode_ReturnsNotFoundError() throws Exception {
        // Arrange
        when(clazzRepository.findById(clazzId)).thenReturn(Optional.empty());

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s", clazzId)));

        // Assert
        result.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is(String.format("Clazz with ID [%s] was not found.", clazzId))));

        verify(clazzRepository).findById(clazzId);
    }
}
