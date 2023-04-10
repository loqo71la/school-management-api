package dev.loqo71la.schoolmanagement.module.common.constant;

/**
 * Constants for end-point
 */
public final class RouteConstants {

    /**
     * Default Constructor.
     */
    private RouteConstants() {
    }

    /**
     * Students end-point.
     */
    public static final String STUDENT_URL = "/api/student";

    /**
     * Classes end-point.
     */
    public static final String CLAZZ_URL = "/api/clazz";

    /**
     * Students by class end-point.
     */
    public static final String STUDENT_BY_CLAZZ_URL = "/api/clazz/{id}/student";

    /**
     * Classes by student end-point.
     */
    public static final String CLAZZ_BY_STUDENT_URL = "/api/student/{id}/clazz";
}
