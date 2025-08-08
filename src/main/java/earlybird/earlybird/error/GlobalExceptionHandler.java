package earlybird.earlybird.error;

import earlybird.earlybird.error.exception.BusinessBaseException;
import earlybird.earlybird.error.exception.auth.apple.LoadAppleP8KeyException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.UnexpectedTypeException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.warn(
                "HttpRequestMethodNotSupportedException for {}: {}",
                request.getRequestURI(),
                e.getMessage());
        return createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessBaseException(
            BusinessBaseException e, HttpServletRequest request) {
        log.warn("BusinessException for {}: {}", request.getRequestURI(), e.getMessage(), e);
        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoResourceFoundException(
            NoResourceFoundException e, HttpServletRequest request) {
        log.warn("NoResourceFoundException for {}: {}", request.getRequestURI(), e.getMessage());
        return createErrorResponseEntity(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(
            Exception e, HttpServletRequest request) {
        log.error("Exception for {}: {}", request.getRequestURI(), e.getMessage(), e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, HttpServletRequest request) {
        log.warn(
                "HttpMessageNotReadableException for {}: {}",
                request.getRequestURI(),
                e.getMessage(),
                e);
        return createErrorResponseEntity(ErrorCode.INCORRECT_REQUEST_BODY_FORMAT);
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        IllegalArgumentException.class,
        UnexpectedTypeException.class,
        HandlerMethodValidationException.class,
        MethodArgumentTypeMismatchException.class,
        MissingRequestHeaderException.class
    })
    protected ResponseEntity<ErrorResponse> handleInvalidRequestArgumentException(
            Exception e, HttpServletRequest request) {
        log.warn(
                "{} for {}: {}",
                e.getClass().getSimpleName(),
                request.getRequestURI(),
                e.getMessage());
        return createErrorResponseEntity(ErrorCode.INVALID_REQUEST_ARGUMENT);
    }

    @ExceptionHandler(LoadAppleP8KeyException.class)
    protected ResponseEntity<ErrorResponse> handleLoadAppleP8KeyException(
            LoadAppleP8KeyException e, HttpServletRequest request) {
        log.warn("LoadAppleP8KeyException for {}: {}", request.getRequestURI(), e.getMessage(), e);
        return createErrorResponseEntity(e.getErrorCode());
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode) {
        return new ResponseEntity<>(ErrorResponse.of(errorCode), errorCode.getStatus());
    }
}
