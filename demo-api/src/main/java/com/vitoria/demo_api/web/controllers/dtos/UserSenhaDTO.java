package com.vitoria.demo_api.web.controllers.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSenhaDTO {
    @NotBlank
    @Size(min = 6,max = 6)
    private String senhaAtual;
    @NotBlank
    @Size(min = 6,max = 6)
    private String novaSenha;
    @NotBlank
    @Size(min = 6,max = 6)
    private String confirmaSenha;
}


