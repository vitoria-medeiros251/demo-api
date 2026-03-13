package com.vitoria.demo_api.web.controllers;
import com.vitoria.demo_api.domain.User;
import com.vitoria.demo_api.services.UserService;
import com.vitoria.demo_api.web.controllers.dtos.UserCreateDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserResponseDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserSenhaDTO;
import com.vitoria.demo_api.web.controllers.mapper.UserMapper;
import com.vitoria.demo_api.web.exception.ErroMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "user", description ="contém todas as operaçoes relativas aos recursos para o cadastro ,edição , e leitura de usuarios")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "criar um novo usuario", description = "recurso para criar novo usuario",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "Usuario email ja cadastrado no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroMessage.class)))
            }
    )

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO userCreateDTO){
        User savedUser = userService.salvar(UserMapper.toUser(userCreateDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserMapper.toDTO(savedUser));
    }


    @GetMapping("/{id}")
   public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id){
        User savedUser = userService.BuscarPorId(id);
        return ResponseEntity.ok(UserMapper.toDTO(savedUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll(){
        List<User> savedUser = userService.BuscarTodos();
        return ResponseEntity.ok(UserMapper.toListDto(savedUser));

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatepassword(@PathVariable Long id,@Valid @RequestBody UserSenhaDTO dto){
        User savedUser = userService.EditarSenha(id, dto.getSenhaAtual(),dto.getNovaSenha(),dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }





}
