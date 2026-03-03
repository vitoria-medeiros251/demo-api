package com.vitoria.demo_api.web.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSenhaDTO {
    private String senhaAtual;
    private String novaSenha;
    private String confirmaSenha;
}


