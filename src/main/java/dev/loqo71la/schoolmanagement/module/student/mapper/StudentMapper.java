package dev.loqo71la.schoolmanagement.module.student.mapper;

import dev.loqo71la.schoolmanagement.module.common.mapper.Mapper;
import dev.loqo71la.schoolmanagement.module.student.controller.StudentDto;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import org.springframework.stereotype.Component;

/**
 * Utility component to mapper students.
 */
@Component
public class StudentMapper implements Mapper<StudentDto, Student> {

    /**
     * Converts student to studentDto.
     *
     * @param student to be converted.
     * @return a studentDto.
     */
    @Override
    public StudentDto toDto(Student student) {
        return new StudentDto(
                student.getId(),
                student.getIdNo(),
                student.getFirstName(),
                student.getLastName(),
                student.getGender(),
                student.getType(),
                student.getLatitude(),
                student.getLongitude(),
                student.getCreatedBy(),
                student.getCreatedAt(),
                student.getUpdatedBy(),
                student.getUpdatedAt()
        );
    }

    /**
     * Converts studentDto to student.
     *
     * @param studentDto to be converted.
     * @return a student.
     */
    @Override
    public Student toModel(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.id());
        student.setIdNo(studentDto.idNo());
        student.setFirstName(studentDto.firstName());
        student.setLastName(studentDto.lastName());
        student.setGender(studentDto.gender());
        student.setType(studentDto.type());
        student.setLatitude(studentDto.latitude());
        student.setLongitude(studentDto.longitude());
        return student;
    }
}
