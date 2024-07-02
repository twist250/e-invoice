package com.qtsoftwareltd.invoicing.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.qtsoftwareltd.invoicing.dto.ApiResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.builder().message("Internal server error").build());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ApiResponse<Object> body = new ApiResponse<>("Method " + ex.getMethod() + " not supported", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorModel> errorMessages = exception.getBindingResult().getFieldErrors().stream().map(err -> new ErrorModel(err.getField(), err.getRejectedValue(), err.getDefaultMessage())).distinct().collect(Collectors.toList());

        return ResponseEntity.badRequest().body(new ApiResponse<>(
                // Send only the first error message for simplicity
                errorMessages.isEmpty() ? "Invalid Request Body" : errorMessages.get(0).getMessageError(), errorMessages));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(new ApiResponse<>("Invalid method argument type. " + ex.getPropertyName() + " should be of type " + ex.getRequiredType().getName()));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable cause = ex.getCause();

        String message;
        if (cause instanceof InvalidFormatException && ((InvalidFormatException) cause).getTargetType().isEnum()) {
            message = "Invalid value: '" + ((InvalidFormatException) cause).getValue() + "' for the field: '" + ((InvalidFormatException) cause).getPath().get(((InvalidFormatException) cause).getPath().size() - 1).getFieldName() + "'. The value must be one of: " + Arrays.toString(((InvalidFormatException) cause).getTargetType().getEnumConstants());
        } else {
            message = "Invalid Request Body";
        }

        logException(ex);
        return ResponseEntity.badRequest().body(new ApiResponse<>(message));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Object>> handleAccessDenied(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>("Access is denied"));
    }

    @ExceptionHandler({AuthenticationException.class, InvalidCredentialException.class})
    public ResponseEntity<ApiResponse<Object>> handleAccessDeniedException(Exception ex) {
        ApiResponse<Object> body = new ApiResponse<>("Invalid Credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(body);
    }

    @ExceptionHandler({BadRequestException.class,})
    public ResponseEntity<ApiResponse<Object>> handleSomeErrors(Exception ex) {
        ApiResponse<Object> body = new ApiResponse<>(ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(Exception ex) {
        ApiResponse<Object> body = new ApiResponse<>(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleInternalError(Exception ex) {
        logException(ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse<>("Something went wrong!"));
    }

    private void logException(Exception ex) {
        logger.error(ex.getMessage(), ex);
    }
}