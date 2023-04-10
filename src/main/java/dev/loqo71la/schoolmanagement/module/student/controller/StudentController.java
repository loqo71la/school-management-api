package dev.loqo71la.schoolmanagement.module.student.controller;

import dev.loqo71la.schoolmanagement.module.common.constant.DtoConstants;
import dev.loqo71la.schoolmanagement.module.common.constant.RouteConstants;
import dev.loqo71la.schoolmanagement.module.common.controller.RestApiController;
import dev.loqo71la.schoolmanagement.module.student.mapper.StudentMapper;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import dev.loqo71la.schoolmanagement.module.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Manages HTTP request for students.
 */
@RestController
@RequestMapping(RouteConstants.STUDENT_URL)
public class StudentController extends RestApiController<StudentDto, Student> {

    /**
     * Auto-injects a proper service.
     *
     * @param studentService CrudService implementation for students.
     */
    @Autowired
    public void setService(StudentService studentService) {
        super.modelService = studentService;
    }

    /**
     * Auto-injects a proper mapper.
     *
     * @param studentMapper StudentMapper implementation.
     */
    @Autowired
    public void setMapper(StudentMapper studentMapper) {
        super.mapper = studentMapper;
    }

    /**
     * Gets the name of the student instance.
     *
     * @return the model name.
     */
    @Override
    protected String getModelName() {
        return DtoConstants.STUDENT_MODEL;
    }

    /**
     * Loads the {@link Sort} instance based of the specific type.
     *
     * @param sort received from http request.
     * @return the sort instance.
     */
    @Override
    protected Sort loadSort(String sort) {
        var order = sort.equals("name") ? new Sort.Order(Sort.Direction.ASC, "name")
                : new Sort.Order(Sort.Direction.DESC, "updatedAt");
        return Sort.by(order.ignoreCase());
    }
}
