package ir.ums.exception;

import ir.ums.constants.ErrorMessages;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;


    private String getLocalizedMessage(String messageKey, HttpServletRequest request) {
        Locale locale = localeResolver.resolveLocale(request);
        return messageSource.getMessage(messageKey, null, locale);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        String parameterValue = !StringUtils.isEmpty(e.getParameterValue()) ? " " + e.getParameterValue() : "";
        var errorResponse = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), getLocalizedMessage(e.getMessage(), request) + parameterValue);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle validation errors (e.g., @Valid or @NotNull validation failures)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
        log.error("exception occurred", e);
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + " " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        var errorResponse = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle invalid parameters in request (e.g., @Min, @Size, @Pattern violations)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("exception occurred", e);
        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
                .collect(Collectors.joining(", "));

        var errorResponse = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(), errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle invalid JSON input
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidJsonException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                getLocalizedMessage(ErrorMessages.INVALID_REQUEST_BODY_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                getLocalizedMessage(ErrorMessages.NOT_FOUND_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoSuchElementException(NoSuchElementException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                getLocalizedMessage(ErrorMessages.NOT_FOUND_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDTO> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.FORBIDDEN.value(),
                getLocalizedMessage(ErrorMessages.ACCESS_DENIED_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.UNAUTHORIZED.value(),
                getLocalizedMessage(ErrorMessages.AUTHENTICATION_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResultException(NoResultException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                getLocalizedMessage(ErrorMessages.NOT_FOUND_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                getLocalizedMessage(ErrorMessages.SERVICE_NOT_FOUND_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                getLocalizedMessage(ErrorMessages.DATA_INTEGRITY_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handle any other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception occurred", e);
        var errorResponse = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getLocalizedMessage(ErrorMessages.INTERNAL_SERVER_EXCEPTION, request)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

