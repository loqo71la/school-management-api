package dev.loqo71la.schoolmanagement.module.clazz.controller;

import dev.loqo71la.schoolmanagement.module.clazz.mapper.ClazzMapper;
import dev.loqo71la.schoolmanagement.module.clazz.service.ClazzService;
import dev.loqo71la.schoolmanagement.module.common.constant.RouteConstants;
import dev.loqo71la.schoolmanagement.module.common.controller.ResultPage;
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
 * Manages HTTP requests for clazzes by student.
 */
@RestController
@RequestMapping(RouteConstants.CLAZZ_BY_STUDENT_URL)
public class ClazzByStudentController {

    /**
     * Stores the clazz service.
     */
    @Autowired
    private ClazzService clazzService;

    /**
     * Stores the clazz mapper.
     */
    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * HTTP GetAll method.
     *
     * @param id    id of selected student.
     * @param page  current page of the pagination.
     * @param limit total item per page.
     * @return a list of studentDto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<ClazzDto>> getAll(@PathVariable UUID id,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "50") int limit) {
        var modelPage = clazzService.readPageByStudent(id, PageRequest.of(page > 0 ? page - 1 : 0, limit));
        return ResponseEntity.ok(new ResultPage<>(
                (int) modelPage.getTotalElements(),
                modelPage.getTotalPages(),
                page,
                modelPage.stream()
                        .map(clazzMapper::toDto)
                        .collect(Collectors.toList())
        ));
    }
}
