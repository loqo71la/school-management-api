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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
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
public class GetAllClazzTest {

    @MockBean
    private ClazzRepository clazzRepository;

    @Autowired
    private TestUtil testUtil;

    @Autowired
    private MockMvc mockMvc;

    private Sort sort;

    @BeforeEach
    public void setup() {
        sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "updatedAt").ignoreCase());
    }

    @Test
    public void GetAllClazzEndpoint_WithoutFilters_ReturnsTheClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 50, sort);
        var clazzList = List.of(
                testUtil.buildClazz(UUID.randomUUID(), "1A-192", "Geology", "Sedimentary Petrology"),
                testUtil.buildClazz(UUID.randomUUID(), "2B-032", "Engineering", "Principles of computational geo-location analysis"),
                testUtil.buildClazz(UUID.randomUUID(), "3C-014", "Music", "Art of Listening")
        );
        when(clazzRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 3));

        // Act
        ResultActions result = mockMvc.perform(get("/api/clazz"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(3)))
                .andExpect(jsonPath("$.totalPage", is(1)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.items[0].code", is("1A-192")))
                .andExpect(jsonPath("$.items[0].title", is("Geology")))
                .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")))
                .andExpect(jsonPath("$.items[1].code", is("2B-032")))
                .andExpect(jsonPath("$.items[1].title", is("Engineering")))
                .andExpect(jsonPath("$.items[1].description", is("Principles of computational geo-location analysis")))
                .andExpect(jsonPath("$.items[2].code", is("3C-014")))
                .andExpect(jsonPath("$.items[2].title", is("Music")))
                .andExpect(jsonPath("$.items[2].description", is("Art of Listening")));

        verify(clazzRepository).findAll(pageable);
    }

    @Test
    public void GetAllClazzEndpoint_WithSizeFilters_ReturnsTheFirstClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(0, 1, sort);
        var clazzList = List.of(testUtil.buildClazz(UUID.randomUUID(), "1A-192", "Geology", "Sedimentary Petrology"));
        when(clazzRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 3));

        // Act
        ResultActions result = mockMvc.perform(get("/api/clazz?limit=1"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(3)))
                .andExpect(jsonPath("$.totalPage", is(3)))
                .andExpect(jsonPath("$.currentPage", is(1)))
                .andExpect(jsonPath("$.items[0].code", is("1A-192")))
                .andExpect(jsonPath("$.items[0].title", is("Geology")))
                .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")));

        verify(clazzRepository).findAll(pageable);
    }

    @Test
    public void GetAllClazzEndpoint_WithPageFilters_ReturnsTheSpecificClazzPage() throws Exception {
        // Arrange
        var pageable = PageRequest.of(3, 2, sort);
        var clazzList = List.of(testUtil.buildClazz(UUID.randomUUID(), "1A-192", "Geology", "Sedimentary Petrology"));
        when(clazzRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(clazzList, pageable, 9));

        // Act
        ResultActions result = mockMvc.perform(get("/api/clazz?limit=2&page=4"));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$.totalItem", is(9)))
                .andExpect(jsonPath("$.totalPage", is(5)))
                .andExpect(jsonPath("$.currentPage", is(4)))
                .andExpect(jsonPath("$.items[0].code", is("1A-192")))
                .andExpect(jsonPath("$.items[0].title", is("Geology")))
                .andExpect(jsonPath("$.items[0].description", is("Sedimentary Petrology")));

        verify(clazzRepository).findAll(pageable);
    }
}
