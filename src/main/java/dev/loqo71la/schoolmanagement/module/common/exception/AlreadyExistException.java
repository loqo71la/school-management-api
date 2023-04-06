package dev.loqo71la.schoolmanagement.module.common.exception;

import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.ALREADY_EXIST;

/**
 * Exception to describe when an operation is unauthorized.
 */
public class AlreadyExistException extends RuntimeException {

    /**
     * Creates a new instance of AlreadyExistException.
     *
     * @param model model name.
     * @param id    model id.
     */
    public AlreadyExistException(String model, String id) {
        super(String.format(ALREADY_EXIST, model, id));
    }
}
