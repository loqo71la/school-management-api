package dev.loqo71la.schoolmanagement.module.common.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wraps a generic http response.
 *
 * @param status  could be 'error' or 'success'.
 * @param message additional description of the executed actions.
 */
public record ResultInfo(
        ResultStatus status,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message
) {
}
