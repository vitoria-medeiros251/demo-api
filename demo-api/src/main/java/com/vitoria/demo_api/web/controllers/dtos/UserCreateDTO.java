package com.vitoria.demo_api.web.controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotBlank
    @Email(message = "email invalido")
    private String username;
    @NotBlank
    @Size(min = 6, max = 6)
    private String password;
}
