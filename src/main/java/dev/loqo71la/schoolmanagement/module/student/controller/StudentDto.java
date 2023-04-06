package dev.loqo71la.schoolmanagement.module.student.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

/**
 * @param id        id of the student.
 * @param idNo      unique idNo of the student.
 * @param firstName student first name.
 * @param lastName  student last name.
 * @param gender    id of the requested gender.
 * @param type      type of the student.
 * @param latitude  latitude coordinate.
 * @param longitude longitude coordinate.
 * @param createdBy username who created the clazz.
 * @param createdAt time it was created.
 * @param updatedBy username who updated the clazz
 * @param updatedAt time it was updated.
 */
public record StudentDto(
        UUID id,
        String idNo,
        String firstName,
        String lastName,
        int gender,
        String type,
        double latitude,
        double longitude,
        String createdBy,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date createdAt,
        String updatedBy,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date updatedAt
) {
}
