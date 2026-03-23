package com.vitoria.demo_api.web.exception;
import com.vitoria.demo_api.exception.EntityNotFoundException;
import com.vitoria.demo_api.exception.PasswordInvalidException;
import com.vitoria.demo_api.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroMessage> entityNotFoundException(RuntimeException ex, HttpServletRequest request){

        log.error("API ERROR - ",ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request.getRequestURI(), request.getMethod(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), null));

    }

    @ExceptionHandler(UsernameUniqueViolationException.class)
    public ResponseEntity<ErroMessage> uniqueViolationException(RuntimeException ex, HttpServletRequest request){

        log.error("API ERROR - ",ex);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request.getRequestURI(), request.getMethod(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), null));

    }


    @ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErroMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request){

        log.error("API ERROR - ",ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErroMessage(request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage(), null));

    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request){

        log.error("API ERROR - ",ex);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErroMessage.of(request, HttpStatus.UNPROCESSABLE_ENTITY, "Campo(s) invalido(s)", ex.getBindingResult()));

    }


}
