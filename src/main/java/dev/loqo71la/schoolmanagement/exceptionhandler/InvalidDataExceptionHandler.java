package dev.loqo71la.schoolmanagement.exceptionhandler;

import dev.loqo71la.schoolmanagement.module.common.controller.ResultInfo;
import dev.loqo71la.schoolmanagement.module.common.controller.ResultStatus;
import dev.loqo71la.schoolmanagement.module.common.exception.AlreadyExistException;
import dev.loqo71la.schoolmanagement.module.common.exception.BadRequestException;
import dev.loqo71la.schoolmanagement.module.common.exception.NotFoundException;
import dev.loqo71la.schoolmanagement.module.common.exception.PermissionDeniedException;
import dev.loqo71la.schoolmanagement.module.common.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles exceptions related to invalid data.
 */
@ControllerAdvice
public class InvalidDataExceptionHandler {

    /**
     * Handler for bad request exception.
     *
     * @param ex bad request instance.
     * @return ResponseEntity with custom exception
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ResultInfo> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for item not found exception.
     *
     * @param ex item not found instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResultInfo> handleItemNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for unauthorized exception.
     *
     * @param ex unauthorized instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ResultInfo> handleUnauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for already exist exception.
     *
     * @param ex already exist instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResultInfo> handleAlreadyExistException(AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Handler for permission denied exception.
     *
     * @param ex permission denied instance.
     * @return ResponseEntity with custom exception.
     */
    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ResultInfo> handlePermissionDeniedException(PermissionDeniedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildResultInfo(ex.getMessage()));
    }

    /**
     * Builds a error result info.
     *
     * @param errorMessage error message.
     * @return a result info.
     */
    private ResultInfo buildResultInfo(String errorMessage) {
        return new ResultInfo(ResultStatus.ERROR, errorMessage);
    }
}
