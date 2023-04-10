package dev.loqo71la.schoolmanagement.module.common.mapper;

/**
 * Utility class to mapper Model to Dto or Dto to Model.
 *
 * @param <T> Dto
 * @param <U>
 */
public interface Mapper<T, U> {

    /**
     * Converts model to dto.
     *
     * @param model to be converted.
     * @return a dto.
     */
    T toDto(U model);

    /**
     * Converts dto to model.
     *
     * @param dto to be converted.
     * @return a model.
     */
    U toModel(T dto);
}
