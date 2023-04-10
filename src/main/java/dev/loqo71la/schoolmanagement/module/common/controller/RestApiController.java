package dev.loqo71la.schoolmanagement.module.common.controller;

import dev.loqo71la.schoolmanagement.config.jwt.JwtTokenProvider;
import dev.loqo71la.schoolmanagement.module.common.exception.PermissionDeniedException;
import dev.loqo71la.schoolmanagement.module.common.mapper.Mapper;
import dev.loqo71la.schoolmanagement.module.common.service.CrudService;
import dev.loqo71la.schoolmanagement.module.common.service.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.stream.Collectors;

import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.CREATE;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.REMOVE;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.SUCCESSFULLY;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.UPDATE;
import static dev.loqo71la.schoolmanagement.module.common.controller.ResultStatus.SUCCESS;

/**
 * Base controller to manage communication between http requests and services.
 *
 * @param <T> model dto.
 * @param <U> model.
 */
public abstract class RestApiController<T, U extends Model> {

    @Autowired
    protected JwtTokenProvider jwtTokenProvider;

    /**
     * Stores the crud service instance.
     */
    protected CrudService<U> modelService;

    /**
     * Store the mapper instance.
     */
    protected Mapper<T, U> mapper;

    /**
     * HTTP GetAll method.
     *
     * @param page  current page of the pagination.
     * @param limit total item per page.
     * @return a list of dto.
     */
    @GetMapping
    public ResponseEntity<ResultPage<T>> getAll(@RequestParam(defaultValue = "1") int page,
                                                @RequestParam(defaultValue = "50") int limit,
                                                @RequestParam(defaultValue = "date") String sort) {
        var modelPage = modelService.readAll(PageRequest.of(page > 0 ? page - 1 : 0, limit, loadSort(sort)));
        return ResponseEntity.ok(new ResultPage<>(
                (int) modelPage.getTotalElements(),
                modelPage.getTotalPages(),
                page,
                modelPage.stream()
                        .map(mapper::toDto)
                        .collect(Collectors.toList())
        ));
    }

    /**
     * HTTP GetById method.
     *
     * @param id of selected dto.
     * @return a single dto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<T> getById(@PathVariable UUID id) {
        T dto = mapper.toDto(modelService.read(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * HTTP Post method.
     *
     * @param dto to be saved.
     * @return the details of the processed request.
     */
    @PostMapping
    public ResponseEntity<ResultInfo> post(@RequestHeader("Authorization") String header, @RequestBody T dto) {
        var model = mapper.toModel(dto);
        var username = jwtTokenProvider.getUsernameFromHeader(header)
                .orElseThrow(() -> new PermissionDeniedException(getModelName(), CREATE));

        model.setCreatedBy(username);
        model.setUpdatedBy(username);
        modelService.create(model);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(success(model.getId(), CREATE));
    }

    /**
     * HTTP Put method.
     *
     * @param id  of selected dto.
     * @param dto to be updated.
     * @return the details of the processed request.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResultInfo> put(@RequestHeader("Authorization") String header, @PathVariable UUID id, @RequestBody T dto) {
        var model = mapper.toModel(dto);
        var username = jwtTokenProvider.getUsernameFromHeader(header)
                .orElseThrow(() -> new PermissionDeniedException(getModelName(), UPDATE));

        model.setUpdatedBy(username);
        modelService.update(id, model);
        return ResponseEntity.ok(success(id, UPDATE));
    }

    /**
     * HTTP Delete method.
     *
     * @param id of selected dto.
     * @return the details of the processed request.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultInfo> delete(@RequestHeader("Authorization") String header, @PathVariable UUID id) {
        jwtTokenProvider.getUsernameFromHeader(header)
                .filter(username -> modelService.isOwner(id, username))
                .orElseThrow(() -> new PermissionDeniedException(getModelName(), REMOVE));
        modelService.delete(id);
        return ResponseEntity.ok(success(id, REMOVE));
    }

    /**
     * Gets the name of the model instance.
     *
     * @return the model name.
     */
    protected abstract String getModelName();

    /**
     * Loads the {@link Sort} instance based of the specific type.
     *
     * @param sort received from http request.
     * @return the sort instance.
     */
    protected abstract Sort loadSort(String sort);

    /**
     * Builds a success http response.
     *
     * @param id     model id.
     * @param action to be set.
     * @return success response
     */
    private ResultInfo success(UUID id, String action) {
        return new ResultInfo(SUCCESS, String.format(SUCCESSFULLY, getModelName(), id, action));
    }
}