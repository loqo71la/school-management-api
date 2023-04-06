package dev.loqo71la.schoolmanagement.module.common.constant;

/**
 * Constants for Response Messages.
 */
public final class ResponseConstants {

    /**
     * Default Constructor.
     */
    private ResponseConstants() {
    }

    /**
     * Stores the pattern message for assigned students.
     */
    public static final String ASSIGNED = "Students were successfully assigned to Clazz with ID [%s].";

    /**
     * Stores the pattern message of successfully action.
     */
    public static final String SUCCESSFULLY = "%s with ID [%s] was successfully %sd.";

    /**
     * Stores the constant 'create'.
     */
    public static final String CREATE = "create";

    /**
     * Stores the constant 'update'.
     */
    public static final String UPDATE = "update";

    /**
     * Stores the constant 'remove'.
     */
    public static final String REMOVE = "remove";

    /**
     * Stores the pattern message for 'already exist' error.
     */
    public static final String ALREADY_EXIST = "%s with ID [%s] already exist.";

    /**
     * Stores the pattern message for 'not found' error.
     */
    public static final String NOT_FOUND = "%s with ID [%s] was not found.";

    /**
     * Stores the pattern message for 'permission denied' error.
     */
    public static final String PERMISSION_DENIED = "You don’t have permission to %s this %s on this server.";

    /**
     * Stores the pattern message for 'bad request' error.
     */
    public static final String BAD_REQUEST = "The request could not be understood by the server due to malformed syntax.";

    /**
     * Stores the pattern message for 'invalid teacher' error.
     */
    public static final String INVALID_TEACHER = "The assigned teacher must be part of the clazz.";
}
