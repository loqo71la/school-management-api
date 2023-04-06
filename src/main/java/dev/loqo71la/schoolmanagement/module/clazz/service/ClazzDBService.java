package dev.loqo71la.schoolmanagement.module.clazz.service;

import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.clazz.repository.ClazzRepository;
import dev.loqo71la.schoolmanagement.module.common.exception.AlreadyExistException;
import dev.loqo71la.schoolmanagement.module.common.exception.BadRequestException;
import dev.loqo71la.schoolmanagement.module.common.exception.NotFoundException;
import dev.loqo71la.schoolmanagement.module.common.service.DBService;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import dev.loqo71la.schoolmanagement.module.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static dev.loqo71la.schoolmanagement.module.common.constant.DtoConstants.CLAZZ_MODEL;
import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.INVALID_TEACHER;

/**
 * Provides methods to handle clazzes.
 */
@Service
public class ClazzDBService extends DBService<Clazz> implements ClazzService {

    /**
     * Auto-injected student repository.
     */
    @Autowired
    private StudentRepository studentRepository;

    /**
     * Auto-injected clazz repository.
     */
    @Autowired
    private ClazzRepository clazzRepository;

    /**
     * Reads a page of students with specific clazz code and page restriction.
     *
     * @param studentId student id.
     * @param pageable  page restriction.
     * @return a page of clazz.
     */
    @Override
    public Page<Clazz> readPageByStudent(UUID studentId, Pageable pageable) {
        return clazzRepository.findPageByStudent(studentId, pageable);
    }

    /**
     * Creates a new Clazz.
     *
     * @param clazz to be created.
     */
    @Override
    public void create(Clazz clazz) {
        if (clazzRepository.existsByCode(clazz.getCode())) {
            throw new AlreadyExistException(CLAZZ_MODEL, clazz.getCode());
        }
        super.create(clazz);
    }

    /**
     * Updates a specific clazz.
     *
     * @param id    clazz id.
     * @param clazz to be updated.
     */
    @Override
    public void update(UUID id, Clazz clazz) {
        var savedClazz = super.read(id);
        if (!Objects.equals(savedClazz.getCode(), clazz.getCode()) && clazzRepository.existsByCode(clazz.getCode())) {
            throw new AlreadyExistException(CLAZZ_MODEL, clazz.getCode());
        }

        savedClazz.setCode(clazz.getCode());
        savedClazz.setTitle(clazz.getTitle());
        savedClazz.setType(clazz.getType());
        savedClazz.setDescription(clazz.getDescription());
        savedClazz.setEnable(clazz.isEnable());
        savedClazz.setLatitude(clazz.getLatitude());
        savedClazz.setLongitude(clazz.getLongitude());
        savedClazz.setUpdatedBy(clazz.getUpdatedBy());
        savedClazz.setUpdatedAt(new Date());
        clazzRepository.save(savedClazz);
    }

    /**
     * Deletes a specific clazz.
     *
     * @param id clazz id.
     */
    @Override
    public void delete(UUID id) {
        var students = studentRepository.findAllByClazz(id);
        students.forEach(student ->
                find(student.getClazzList(), id).ifPresent(clazz -> student.getClazzList().remove(clazz))
        );

        studentRepository.saveAll(students);
        clazzRepository.deleteById(id);
    }

    /**
     * Checks if user owns clazz.
     *
     * @param id        model id.
     * @param createdBy to be checked.
     * @return true if it is the owner, otherwise false.
     */
    @Override
    public boolean isOwner(UUID id, String createdBy) {
        return clazzRepository.existsByIdAndCreatedBy(id, createdBy);
    }

    /**
     * Assign the student list and teacher to specific clazz.
     *
     * @param clazzId    clazz id.
     * @param teacherId  teacher id.
     * @param studentIds list of students.
     */
    @Override
    public void assignStudent(UUID clazzId, UUID teacherId, List<UUID> studentIds) {
        var clazz = super.read(clazzId);
        if (teacherId != null && !studentIds.contains(teacherId)) {
            throw new BadRequestException(INVALID_TEACHER);
        }

        var students = studentRepository.findAllByIds(studentIds);
        studentIds.removeAll(students.stream().map(Student::getId).toList());
        if (!studentIds.isEmpty()) {
            throw new NotFoundException(CLAZZ_MODEL, String.join(", ", studentIds.stream().map(UUID::toString).toList()));
        }

        clazz.setTotalAssigned(students.size());
        clazz.setStudentList(students);
        clazz.setTeacherId(teacherId);
        clazzRepository.save(clazz);
    }

    /**
     * Gets the current clazz repository.
     *
     * @return the repository.
     */
    @Override
    protected PagingAndSortingRepository<Clazz, UUID> getModelRepository() {
        return clazzRepository;
    }

    /**
     * Gets the current clazz name.
     *
     * @return the name.
     */
    @Override
    protected String getModelName() {
        return CLAZZ_MODEL;
    }
}
