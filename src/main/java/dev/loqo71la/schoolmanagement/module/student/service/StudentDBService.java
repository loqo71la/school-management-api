package dev.loqo71la.schoolmanagement.module.student.service;

import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
import dev.loqo71la.schoolmanagement.module.common.exception.AlreadyExistException;
import dev.loqo71la.schoolmanagement.module.common.service.DBService;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import dev.loqo71la.schoolmanagement.module.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static dev.loqo71la.schoolmanagement.module.common.constant.DtoConstants.STUDENT_MODEL;

/**
 * Provides methods to handle students.
 */
@Service
public class StudentDBService extends DBService<Student> implements StudentService {

    /**
     * Auto-injected clazz repository.
     */
    @Autowired
    private ClazzRepository clazzRepository;

    /**
     * Auto-injected student repository.
     */
    @Autowired
    private StudentRepository studentRepository;

    /**
     * Reads a page of students with specific clazz code and page restriction.
     *
     * @param clazzId clazz id.
     * @param pageable  page restriction.
     * @return a page of student.
     */
    @Override
    public Page<Student> readPageByClazz(UUID clazzId, Pageable pageable) {
        return studentRepository.findPageByClazz(clazzId, pageable);
    }

    /**
     * Creates a new student.
     *
     * @param student to be created.
     */
    @Override
    public void create(Student student) {
        if (studentRepository.existsByIdNo(student.getIdNo())) {
            throw new AlreadyExistException(STUDENT_MODEL, student.getIdNo());
        }
        super.create(student);
    }

    /**
     * Updates a specific student.
     *
     * @param id      student id.
     * @param student to be updated.
     */
    @Override
    public void update(UUID id, Student student) {
        Student savedStudent = super.read(id);
        if (!Objects.equals(savedStudent.getIdNo(), student.getIdNo()) && studentRepository.existsByIdNo(student.getIdNo())) {
            throw new AlreadyExistException(STUDENT_MODEL, student.getIdNo());
        }

        savedStudent.setIdNo(student.getIdNo());
        savedStudent.setFirstName(student.getFirstName());
        savedStudent.setLastName(student.getLastName());
        savedStudent.setGender(student.getGender());
        savedStudent.setType(student.getType());
        savedStudent.setLatitude(student.getLatitude());
        savedStudent.setLongitude(student.getLongitude());
        savedStudent.setUpdatedBy(student.getUpdatedBy());
        savedStudent.setUpdatedAt(new Date());
        studentRepository.save(savedStudent);
    }

    /**
     * Deletes a specific student.
     *
     * @param id model id
     */
    @Override
    public void delete(UUID id) {
        var clazzes = clazzRepository.findAllByStudent(id);
        clazzes.forEach(clazz -> {
            find(clazz.getStudentList(), id).ifPresent(student -> clazz.getStudentList().remove(student));
            clazz.setTotalAssigned(clazz.getStudentList().size());
            if (Objects.equals(clazz.getTeacherId(), id)) {
                clazz.setTeacherId(null);
            }
        });

        clazzRepository.saveAll(clazzes);
        studentRepository.deleteById(id);
    }

    /**
     * Checks if user owns student.
     *
     * @param id        model id.
     * @param createdBy to be checked.
     * @return true if it is the owner, otherwise false.
     */
    @Override
    public boolean isOwner(UUID id, String createdBy) {
        return studentRepository.existsByIdAndCreatedBy(id, createdBy);
    }

    /**
     * Gets the current student repository.
     *
     * @return the repository.
     */
    @Override
    protected PagingAndSortingRepository<Student, UUID> getModelRepository() {
        return studentRepository;
    }

    /**
     * Gets the current student name.
     *
     * @return the student name.
     */
    @Override
    protected String getModelName() {
        return STUDENT_MODEL;
    }
}
