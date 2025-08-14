package com.romands.ConfigSecurity;

import com.romands.Execeptions.EmailRegisteredException;
import com.romands.Execeptions.InvalidQuantityException;
import com.romands.Execeptions.ProductNotFounded;
import com.romands.Execeptions.UsernameNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    record ErrorResponse(String message, int status, Instant timestamp) {}

    @ExceptionHandler(EmailRegisteredException.class)
    public ResponseEntity<ErrorResponse> handleEmailRegisteredException(EmailRegisteredException e) {
        var error = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {
        var error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInvalidQuantityException(InvalidQuantityException e) {
        var error = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ProductNotFounded.class)
    public ResponseEntity<ErrorResponse> handleProductNotFoundedException(ProductNotFounded e) {
        var error = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            WebRequest request)
    {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        var error = new ErrorResponse(errors.toString(), HttpStatus.BAD_REQUEST.value(), Instant.now());
        return ResponseEntity.badRequest().body(error);
    }
}
