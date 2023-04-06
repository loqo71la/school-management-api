package dev.loqo71la.schoolmanagement.module.student.service;

import dev.loqo71la.schoolmanagement.module.common.service.CrudService;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Defines additional methods to handle students.
 */
public interface StudentService extends CrudService<Student> {

    /**
     * Reads a page of students with specific clazz code and page restriction.
     *
     * @param clazzId clazz id.
     * @param pageable  page restriction.
     * @return a page of student.
     */
    Page<Student> readPageByClazz(UUID clazzId, Pageable pageable);
}
