package dev.loqo71la.schoolmanagement.module.clazz.mapper;

import dev.loqo71la.schoolmanagement.module.clazz.controller.ClazzDto;
import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.common.mapper.Mapper;
import org.springframework.stereotype.Component;

/**
 * Utility component to mapper clazzes.
 */
@Component
public class ClazzMapper implements Mapper<ClazzDto, Clazz> {

    /**
     * Converts clazz to clazzDto.
     *
     * @param clazz to be converted.
     * @return a clazzDto.
     */
    @Override
    public ClazzDto toDto(Clazz clazz) {
        return new ClazzDto(
                clazz.getId(),
                clazz.getCode(),
                clazz.getType(),
                clazz.getTitle(),
                clazz.getDescription(),
                clazz.getTotalAssigned(),
                clazz.getTeacherId(),
                clazz.getLatitude(),
                clazz.getLongitude(),
                clazz.getCreatedBy(),
                clazz.getCreatedAt(),
                clazz.getUpdatedBy(),
                clazz.getUpdatedAt(),
                clazz.isEnable()
        );
    }

    /**
     * Converts clazzDto to clazz.
     *
     * @param clazzDto to be converted.
     * @return a clazz.
     */
    @Override
    public Clazz toModel(ClazzDto clazzDto) {
        Clazz clazz = new Clazz();
        clazz.setId(clazzDto.id());
        clazz.setCode(clazzDto.code());
        clazz.setType(clazzDto.type());
        clazz.setTitle(clazzDto.title());
        clazz.setDescription(clazzDto.description());
        clazz.setLatitude(clazzDto.latitude());
        clazz.setLongitude(clazzDto.longitude());
        clazz.setEnable(clazzDto.enable());
        return clazz;
    }
}
