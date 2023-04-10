package dev.loqo71la.schoolmanagement.student;

import dev.loqo71la.schoolmanagement.TestUtil;
import dev.loqo71la.schoolmanagement.module.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.empty;
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
public class GetAllStudentByClazzTest {

    @MockBean
    private StudentRepository studentRepository;

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
    public void GetAllStudentByClazzEndpoint_WithoutFilters_ReturnsTheStudentPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 50);
        var clazzList = List.of(
                testUtil.buildStudent(UUID.randomUUID(), "00001", "Jose", "Perez"),
                testUtil.buildStudent(UUID.randomUUID(), "00002", "Maria", "Estrada")
        );
        when(studentRepository.findPageByClazz(clazzId, pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 2));

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s/student", clazzId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(2)))
                .andExpect(jsonPath("$.totalPage", is(1)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.items[0].idNo", is("00001")))
                .andExpect(jsonPath("$.items[0].firstName", is("Jose")))
                .andExpect(jsonPath("$.items[0].lastName", is("Perez")))
                .andExpect(jsonPath("$.items[1].idNo", is("00002")))
                .andExpect(jsonPath("$.items[1].firstName", is("Maria")))
                .andExpect(jsonPath("$.items[1].lastName", is("Estrada")));

        verify(studentRepository).findPageByClazz(clazzId, pageable);
    }

    @Test
    public void GetAllStudentByClazzEndpoint_WithInvalidClazz_ReturnsEmptyStudentPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 50);
        when(studentRepository.findPageByClazz(clazzId, pageable))
                .thenReturn(new PageImpl<>(List.of(), pageable, 0));

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s/student", clazzId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(0)))
                .andExpect(jsonPath("$.totalPage", is(0)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.items", is(empty())));

        verify(studentRepository).findPageByClazz(clazzId, pageable);
    }

    @Test
    public void GetAllStudentByClazzEndpoint_WithSizeFilters_ReturnsTheFirstStudentPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 1);
        var clazzList = List.of(testUtil.buildStudent(UUID.randomUUID(), "00001", "Jose", "Perez"));
        when(studentRepository.findPageByClazz(clazzId, pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 3));

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s/student?limit=1", clazzId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(3)))
                .andExpect(jsonPath("$.totalPage", is(3)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.items[0].idNo", is("00001")))
                .andExpect(jsonPath("$.items[0].firstName", is("Jose")))
                .andExpect(jsonPath("$.items[0].lastName", is("Perez")));

        verify(studentRepository).findPageByClazz(clazzId, pageable);
    }

    @Test
    public void GetAllStudentByClazzEndpoint_WithPageFilters_ReturnsTheSpecificStudentPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(3, 2);
        var clazzList = List.of(testUtil.buildStudent(UUID.randomUUID(), "00001", "Jose", "Perez"));
        when(studentRepository.findPageByClazz(clazzId, pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 9));

        // Act
        ResultActions result = mockMvc.perform(get(String.format("/api/clazz/%s/student?limit=2&page=4", clazzId)));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(9)))
                .andExpect(jsonPath("$.totalPage", is(5)))
                .andExpect(jsonPath("$.currentPage", is(4)))
                .andExpect(jsonPath("$.items[0].idNo", is("00001")))
                .andExpect(jsonPath("$.items[0].firstName", is("Jose")))
                .andExpect(jsonPath("$.items[0].lastName", is("Perez")));

        verify(studentRepository).findPageByClazz(clazzId, pageable);
    }
}
