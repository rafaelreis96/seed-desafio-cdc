package dev.eficiente.lojavirtual.exception;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    private static final String MSG_SERVER_ERROR = "Ocorreu um erro interno. Tente novamente mais tarde.";

    private static final String MSG_VALIDATION_ERROR = "Um ou mais campos obrigatórios não foram preenchidos.";

    private static final String MSG_RESOURCE_NOT_FOUND = "Recurso não encontrado.";

    private final MessageSource messageSource;
    private final WebRequest webRequest;

    public ErrorHandler(MessageSource messageSource, WebRequest webRequest) {
        this.messageSource = messageSource;
        this.webRequest = webRequest;
    }

    private ErrorMessage buildMessage(FieldError field) {
        return new ErrorMessage(this.messageSource.getMessage(field, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> serverError(Exception e) {
        String requestUrl = webRequest.getDescription(false);
        var response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), MSG_SERVER_ERROR, requestUrl, List.of());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException e) {
        String requestUrl = webRequest.getDescription(false);
        var error = new ErrorMessage(e.getMessage());
        var response = new ErrorResponse(HttpStatus.NOT_FOUND.value(), MSG_RESOURCE_NOT_FOUND, requestUrl, List.of(error));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String target = webRequest.getDescription(false);
        List<ErrorMessage> errors = e.getFieldErrors().stream()
                .map(this::buildMessage)
                .toList();

        var response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), MSG_VALIDATION_ERROR, target, errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}