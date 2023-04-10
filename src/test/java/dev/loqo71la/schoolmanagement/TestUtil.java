package dev.loqo71la.schoolmanagement;

import dev.loqo71la.schoolmanagement.module.clazz.repository.Clazz;
import dev.loqo71la.schoolmanagement.module.student.repository.Student;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.UUID;

@Component
public class TestUtil {

    @Value("${spring.jwt.secret}")
    private String secret;

    public Clazz buildClazz(UUID id, String clazzCode, String title, String description) {
        var clazz = new Clazz();
        clazz.setId(id);
        clazz.setCode(clazzCode);
        clazz.setTitle(title);
        clazz.setDescription(description);
        clazz.setStudentList(new ArrayList<>());
        return clazz;
    }

    public Student buildStudent(UUID id, String idNo, String firstName, String lastName) {
        var student = new Student();
        student.setId(id);
        student.setIdNo(idNo);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setClazzList(new ArrayList<>());
        return student;
    }

    public String buildToken() {
        var key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
        var jwt = Jwts.builder()
                .claim("username", "test")
                .signWith(key)
                .compact();;
        return String.format("Bearer %s", jwt);
    }
}
