package dev.loqo71la.schoolmanagement.module.common.exception;

/**
 * Exception to describe when a operation is unauthorized.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Creates a new instance of UnauthorizedException.
     *
     * @param message Exception message.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of UnauthorizedException.
     *
     * @param message Exception message.
     * @param cause   Throwable cause
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
