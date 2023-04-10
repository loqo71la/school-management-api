package dev.loqo71la.schoolmanagement.module.common.exception;

import static dev.loqo71la.schoolmanagement.module.common.constant.ResponseConstants.PERMISSION_DENIED;

/**
 * Exception when access permissions were denied.
 */
public class PermissionDeniedException extends RuntimeException {

    /**
     * Creates a new instance of PermissionDeniedException.
     *
     * @param model  model name.
     * @param action to be executed.
     */
    public PermissionDeniedException(String model, String action) {
        super(String.format(PERMISSION_DENIED, action, model));
    }
}
