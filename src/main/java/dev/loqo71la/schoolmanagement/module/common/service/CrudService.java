package dev.loqo71la.schoolmanagement.module.common.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Defines the CRUD methods.
 *
 * @param <T> represents a model.
 */
public interface CrudService<T> {

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    void create(T model);

    /**
     * Reads all models with specific filter.
     *
     * @param pageable request pagination fields.
     * @return the list of models.
     */
    Page<T> readAll(Pageable pageable);

    /**
     * Reads a single model for specific id.
     *
     * @param id model id.
     * @return the model.
     */
    T read(UUID id);

    /**
     * Updates a specific model.
     *
     * @param id    model id.
     * @param model to be updated.
     */
    void update(UUID id, T model);

    /**
     * Deletes and specific model.
     *
     * @param id model id.
     */
    void delete(UUID id);

    /**
     * Checks if user owns model.
     *
     * @param id        model id.
     * @param createdBy to be checked.
     * @return true if it is the owner, otherwise false.
     */
    boolean isOwner(UUID id, String createdBy);
}
