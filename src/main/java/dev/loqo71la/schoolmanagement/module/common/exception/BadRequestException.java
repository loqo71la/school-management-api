package dev.loqo71la.schoolmanagement.module.common.exception;

import dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants;

/**
 * Exception to describe a malformed request.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Creates a new instance of BadRequestException.
     */
    public BadRequestException() {
        this(ResponseConstants.BAD_REQUEST);
    }

    /**
     * Creates a new instance of BasRequestException with specific message.
     *
     * @param message to be set.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
