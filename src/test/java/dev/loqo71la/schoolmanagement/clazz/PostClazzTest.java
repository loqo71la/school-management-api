package dev.loqo71la.schoolmanagement.clazz;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TestUtil testUtil;

    private MockMvc mockMvc;

    private UUID clazzId;

    @BeforeEach
    public void setup() {
        clazzId = UUID.randomUUID();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void PostClazzEndpoint_WithNotExistingCode_ReturnsSuccess() throws Exception {
        // Arrange
        when(clazzRepository.save(any(Clazz.class))).thenAnswer(args -> {
            var clazz = (Clazz) args.getArgument(0);
            clazz.setId(clazzId);
            return clazz;
        });

        // Act
        ResultActions result = mockMvc.perform(post("/api/clazz")
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1A-192\", \"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"));

        // Assert
        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", is(String.format("Clazz with ID [%s] was successfully created.", clazzId))));

        verify(clazzRepository).existsByCode("1A-192");
        verify(clazzRepository).save(any(Clazz.class));
    }


    @Test
    public void PostClazzEndpoint_WithExistingClazzCode_ReturnsAlreadyExistError() throws Exception {
        // Arrange
        when(clazzRepository.existsByCode("1A-192")).thenReturn(true);

        // Act
        ResultActions result = mockMvc.perform(post("/api/clazz")
                .header("Authorization", testUtil.buildToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"code\": \"1A-192\", \"title\": \"Art Hitory\", \"description\": \"Art Hitory\"}"));

        // Assert
        result.andExpect(status().isConflict())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Clazz with ID [1A-192] already exist.")));

        verify(clazzRepository).existsByCode("1A-192");
        verify(clazzRepository, never()).save(any(Clazz.class));
    }
}
