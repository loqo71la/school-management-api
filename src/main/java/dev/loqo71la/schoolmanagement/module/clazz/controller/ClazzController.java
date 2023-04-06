package dev.loqo71la.schoolmanagement.module.clazz.controller;

import dev.loqo71la.schoolmanagement.module.clazz.mapper.ClazzMapper;
import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.clazz.service.ClazzService;
import dev.loqo71la.schoolmanagement.module.common.constant.RouteConstants;
import dev.loqo71la.schoolmanagement.module.common.controller.AssignDto;
import dev.loqo71la.schoolmanagement.module.common.controller.RestApiController;
import dev.loqo71la.schoolmanagement.module.common.controller.ResultInfo;
import dev.loqo71la.schoolmanagement.module.common.exception.PermissionDeniedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static dev.loqo71la.schoolmanagement.module.common.constant.DtoConstants.CLAZZ_MODEL;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.ASSIGNED;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.CREATE;
import static dev.loqo71la.schoolmanagement.module.common.controller.ResultStatus.SUCCESS;

/**
 * Manages the http request for clazzes.
 */
@RestController
@RequestMapping(RouteConstants.CLAZZ_URL)
public class ClazzController extends RestApiController<ClazzDto, Clazz> {

    /**
     * Auto-injects a proper service.
     *
     * @param clazzService CrudService implementation for classes.
     */
    @Autowired
    public void setService(ClazzService clazzService) {
        super.modelService = clazzService;
    }

    /**
     * Auto-injects a proper mapper.
     *
     * @param clazzMapper ClazzMapper implementation.
     */
    @Autowired
    public void setMapper(ClazzMapper clazzMapper) {
        super.mapper = clazzMapper;
    }

    /**
     * HTTP Post method to assign students.
     *
     * @param header    authentication header.
     * @param id        id of the selected clazz.
     * @param assignDto to be saved.
     * @return the details of the processed request.
     */
    @PostMapping("/{id}/assign")
    public ResponseEntity<ResultInfo> assign(@RequestHeader("Authorization") String header, @PathVariable UUID id, @RequestBody AssignDto assignDto) {
        jwtTokenProvider.getUsernameFromHeader(header)
                .orElseThrow(() -> new PermissionDeniedException(getModelName(), CREATE));

        var service = (ClazzService) modelService;
        service.assignStudent(id, assignDto.teacherId(), assignDto.studentIds());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResultInfo(SUCCESS, String.format(ASSIGNED, id)));
    }

    /**
     * Gets the name of the clazz instance.
     *
     * @return the model name.
     */
    @Override
    protected String getModelName() {
        return CLAZZ_MODEL;
    }

    /**
     * Loads the {@link Sort} instance based of the specific type.
     *
     * @param sort received from http request.
     * @return the sort instance.
     */
    @Override
    protected Sort loadSort(String sort) {
        var order = sort.equals("title") ? new Sort.Order(Sort.Direction.ASC, "title")
                : sort.equals("assigned") ? new Sort.Order(Sort.Direction.DESC, "totalAssigned")
                : new Sort.Order(Sort.Direction.DESC, "updatedAt");
        return Sort.by(order.ignoreCase());
    }
}
