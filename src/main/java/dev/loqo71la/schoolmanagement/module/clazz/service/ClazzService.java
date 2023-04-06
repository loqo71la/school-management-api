package dev.loqo71la.schoolmanagement.module.clazz.service;

import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.common.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * Defines additional methods to handle clazzes.
 */
public interface ClazzService extends CrudService<Clazz> {

    /**
     * Reads a page of students with specific clazz code and page restriction.
     *
     * @param studentId student id.
     * @param pageable  page restriction.
     * @return a page of clazz.
     */
    Page<Clazz> readPageByStudent(UUID studentId, Pageable pageable);

    /**
     * Assign the student list and teacher to specific clazz.
     *
     * @param clazzId    clazz id.
     * @param teacherId  teacher id.
     * @param studentIds list of students.
     */
    void assignStudent(UUID clazzId, UUID teacherId, List<UUID> studentIds);
}
