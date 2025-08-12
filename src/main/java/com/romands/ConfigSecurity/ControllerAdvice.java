package com.romands.ConfigSecurity;

import com.romands.Execeptions.EmailRegisteredException;
import com.romands.Execeptions.UsernameNotFoundException;
import com.romands.Execeptions.InvalidQuantityException;
import com.romands.Execeptions.ProductNotFounded;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmailRegisteredException.class)
    private ResponseEntity<?> EmailRegisteredException(EmailRegisteredException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<?> UsernameNotFoundException(UsernameNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(InvalidQuantityException.class)
    private ResponseEntity<?> invalidQuantity(InvalidQuantityException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
    @ExceptionHandler(ProductNotFounded.class)
    private ResponseEntity<?> ProductNotFounded(ProductNotFounded exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            org.springframework.http.HttpStatusCode status,
            org.springframework.web.context.request.WebRequest request) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        return ResponseEntity.badRequest().body(errors);
    }

}

