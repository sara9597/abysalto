package hr.abysalto.hiring.mid.service.exception;


import hr.abysalto.hiring.mid.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Resource not found: " + ex.getMessage(),
                "NOT_FOUND"
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Invalid request: " + ex.getMessage(),
                "BAD_REQUEST"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Conflict: " + ex.getMessage(),
                "CONFLICT"
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Invalid parameter type for '" + ex.getName() + "': expected " + ex.getRequiredType().getSimpleName(),
                "BAD_REQUEST"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Database constraint violation: " + ex.getMessage(),
                "CONSTRAINT_VIOLATION"
        );
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "Internal server error: " + ex.getMessage(),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(
                "An unexpected error occurred: " + ex.getMessage(),
                "INTERNAL_SERVER_ERROR"
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}