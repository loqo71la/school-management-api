package dev.loqo71la.schoolmanagement.module.student.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, UUID> {

    /**
     * Returns a page of students with the given clazz id and page restriction.
     *
     * @param clazzId  clazz id
     * @param pageable page restriction.
     * @return a page of students.
     */
    @Query("SELECT s FROM Student s JOIN s.clazzList c WHERE c.id = :clazzId")
    Page<Student> findPageByClazz(UUID clazzId, Pageable pageable);

    /**
     * Returns all students with the given student id.
     *
     * @param clazzId clazz id.
     * @return the list of student.
     */
    @Query("SELECT s FROM Student s JOIN s.clazzList c WHERE c.id = :clazzId")
    List<Student> findAllByClazz(UUID clazzId);

    /**
     * Returns all students with the given list of student id.
     *
     * @param ids list of student id.
     * @return list of student.
     */
    @Query("SELECT s FROM Student s WHERE s.id IN :ids")
    List<Student> findAllByIds(List<UUID> ids);

    /**
     * Returns whether a student with the given idNo exists.
     *
     * @param idNo unique student idNo.
     * @return true if a student exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.idNo = :idNo")
    boolean existsByIdNo(String idNo);

    /**
     * Returns whether a student with the given id and createdBy properties exists.
     *
     * @param id        student id
     * @param createdBy who created the student.
     * @return true if a student exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.id = :id AND s.createdBy = :createdBy")
    boolean existsByIdAndCreatedBy(UUID id, String createdBy);
}
