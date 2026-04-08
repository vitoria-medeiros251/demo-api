package com.vitoria.demo_api;

import com.vitoria.demo_api.web.controllers.dtos.UserCreateDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserResponseDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserSenhaDTO;
import com.vitoria.demo_api.web.exception.ErroMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/users/users-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/users/users-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserIT {
    @Autowired
    WebTestClient testClient;


    @Test
    public void createUser_ComUsernameEPassowordValidos_RetornarUserCreateComStatus201() {

        UserResponseDTO responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("tody@email.com", "123456"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENT");

    }

    @Test
    public void createdUser_ComUsernameInvalido_RetornarErrorMensagemStatus422() {

        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("today@", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("today@email", "123456"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createdUser_ComPasswordInvalido_RetornarErrorMensagemStatus422() {

        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("tody@email.com", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("today@", "123"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("today@email", "123456789"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


    @Test
    public void createUser_ComUsernameRepetido_RetornarErrorMessageComStatus409() {

        ErroMessage responseBody = testClient
                .post()
                .uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserCreateDTO("ana@email.com", "123456"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);

    }

    @Test
    public void BuscarUser_ComIdExistente_RetornarUserComStatus200() {

        UserResponseDTO responseBody = testClient
                .get()
                .uri("/api/v1/users/100")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserResponseDTO.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

    }


    @Test
    public void BuscarUser_ComIdInexitente_RetornarUserComStatus404() {

        ErroMessage responseBody = testClient
                .get()
                .uri("/api/v1/users/0")
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }

    @Test
    public void editarsenha_ComDadosValidos_RetornarUserCreateComStatus204() {

        testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("123456","123410","123410"))
                .exchange()
                .expectStatus().isNoContent();




    }



    @Test
    public void EditarSenha_ComIdInexitente_RetornarUserComStatus404() {

        ErroMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/0")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("123456","123410","123410"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);

    }


    @Test
    public void EditarSenha_ComCamposInvalidos_RetornarUserComStatus422() {

        ErroMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("","",""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

                  responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("1234567","1234567","1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);






    }



    @Test
    public void EditarSenha_ComCamposInvalidos_RetornarUserComStatus400() {

        ErroMessage responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("123456","123410","000000"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
                .patch()
                .uri("/api/v1/users/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UserSenhaDTO("000000","123410","123410"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErroMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);






    }






}
