package dev.loqo71la.schoolmanagement.module.common.controller;

import java.util.List;
import java.util.UUID;

/**
 * Wraps an assign model.
 *
 * @param teacherId  id of the requested teacher.
 * @param studentIds list of the requested student ids.
 */
public record AssignDto(
        UUID teacherId,
        List<UUID> studentIds
) {
}
