package com.vitoria.demo_api.web.exception;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import java.util.Map;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ErroMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String,String> errors;

    public static ErroMessage of(HttpServletRequest request, HttpStatus status, String message, BindingResult result){
        ErroMessage erroMessage = ErroMessage.builder()
                .path(request.getRequestURI())
                .method(request.getMethod())
                .status(status.value())
                .statusText(status.getReasonPhrase())
                .message(message)
                .build();
        erroMessage.addErrors(result);
        return erroMessage;
    }

    private void addErrors(BindingResult result){
        this.errors = new java.util.HashMap<>();
        result.getFieldErrors().forEach(error -> 
            this.errors.put(error.getField(), error.getDefaultMessage())
        );
    }
    

}
