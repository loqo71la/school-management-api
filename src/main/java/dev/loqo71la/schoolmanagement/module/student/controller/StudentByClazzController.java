package dev.loqo71la.schoolmanagement.module.student.controller;

import dev.loqo71la.schoolmanagement.module.common.constant.RouteConstants;
import dev.loqo71la.schoolmanagement.module.common.controller.ResultPage;
import dev.loqo71la.schoolmanagement.module.student.mapper.StudentMapper;
import dev.loqo71la.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Manages HTTP request for students by clazz.
 */
@RestController
@RequestMapping(RouteConstants.STUDENT_BY_CLAZZ_URL)
public class StudentByClazzController {

    /**
     * Stores the clazz volatile service.
     */
    @Autowired
    private StudentService studentService;

    /**
     * Stores the student mapper.
     */
    @Autowired
    private StudentMapper studentMapper;

    /**
     * HTTP GetAll method.
     *
     * @param id    code of selected clazz.
     * @param page  current page of the pagination.
     * @param limit total item per page.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<StudentDto>> getAll(@PathVariable UUID id,
                                                         @RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "50") int limit) {
        var modelPage = studentService.readPageByClazz(id, PageRequest.of(page > 0 ? page - 1 : 0, limit));
        return ResponseEntity.ok(new ResultPage<>(
                (int) modelPage.getTotalElements(),
                modelPage.getTotalPages(),
                page,
                modelPage.stream()
                        .map(studentMapper::toDto)
                        .collect(Collectors.toList())
        ));
    }
}
