package dev.loqo71la.schoolmanagement.module.clazz.controller;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.UUID;

/**
 * Wraps a clazz.
 *
 * @param id            id of the clazz.
 * @param code          unique clazz code.
 * @param type          type of the clazz.
 * @param title         title of the clazz.
 * @param description   additional description
 * @param totalAssigned total number of the assigned students.
 * @param teacherId     id of the assigned teacher.
 * @param latitude      latitude coordinate.
 * @param longitude     longitude coordinate.
 * @param createdBy     username who created the clazz.
 * @param createdAt     time it was created.
 * @param updatedBy     username who updated the clazz
 * @param updatedAt     time it was updated.
 * @param enable        state of clazz.
 */
public record ClazzDto(
        UUID id,
        String code,
        String type,
        String title,
        String description,
        int totalAssigned,
        UUID teacherId,
        double latitude,
        double longitude,
        String createdBy,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date createdAt,
        String updatedBy,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date updatedAt,
        boolean enable
) {
}
