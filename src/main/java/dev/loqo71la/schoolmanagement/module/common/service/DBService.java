package dev.loqo71la.schoolmanagement.module.common.service;

import dev.loqo71la.schoolmanagement.module.common.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class DBService<T extends Model> implements CrudService<T> {

    /**
     * Reads all models with specific filter.
     *
     * @param pageable request pagination fields.
     * @return the list of models.
     */
    @Override
    public Page<T> readAll(Pageable pageable) {
        return getModelRepository().findAll(pageable);
    }

    /**
     * Creates a new model.
     *
     * @param model to be created.
     */
    @Override
    public void create(T model) {
        var now = new Date();
        model.setCreatedAt(now);
        model.setUpdatedAt(now);
        getModelRepository().save(model);
    }

    /**
     * Reads a single model for specific id.
     *
     * @param id model id.
     * @return the model.
     */
    @Override
    public T read(UUID id) {
        return getModelRepository()
                .findById(id)
                .orElseThrow(() -> new NotFoundException(getModelName(), String.valueOf(id)));
    }

    /**
     * Utility method to find a model by id from a generic list.
     *
     * @param modelList generic list.
     * @param id        to be found.
     * @return the model.
     */
    protected Optional<T> find(List<T> modelList, UUID id) {
        if (modelList == null || modelList.isEmpty()) {
            return Optional.empty();
        }

        return modelList.stream()
                .filter(model -> Objects.equals(model.getId(), id))
                .findFirst();
    }

    /**
     * Gets the current model repository.
     *
     * @return the repository.
     */
    protected abstract PagingAndSortingRepository<T, UUID> getModelRepository();

    /**
     * Gets the current model name.
     *
     * @return the name.
     */
    protected abstract String getModelName();
}
