package dev.loqo71la.schoolmanagement.module.clazz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides additional methods to retrieve clazzes using the pagination abstraction.
 */
@Repository
public interface ClazzRepository extends PagingAndSortingRepository<Clazz, UUID> {

    /**
     * Returns a page of clazzes with the given student id and page restriction.
     *
     * @param studentId student id
     * @param pageable  page restriction.
     * @return a page of clazzes.
     */
    @Query("select c from Clazz c inner join c.studentList s where s.id = :studentId")
    Page<Clazz> findPageByStudent(UUID studentId, Pageable pageable);

    /**
     * Returns all clazzes with the given student id.
     *
     * @param studentId student id.
     * @return the list of clazz.
     */
    @Query("select c from Clazz c inner join c.studentList s where s.id = :studentId")
    List<Clazz> findAllByStudent(UUID studentId);

    /**
     * Returns whether a clazz with the given code exists.
     *
     * @param code clazz code.
     * @return true if a clazz exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Clazz c WHERE c.code = :code")
    boolean existsByCode(String code);

    /**
     * Returns whether a clazz with the given id and createdBy properties exists.
     *
     * @param id        clazz id
     * @param createdBy who created the clazz.
     * @return true if a clazz exists, false otherwise.
     */
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Clazz c WHERE c.id = :id AND c.createdBy = :createdBy")
    boolean existsByIdAndCreatedBy(UUID id, String createdBy);
}
