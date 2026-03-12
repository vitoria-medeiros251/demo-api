package com.vitoria.demo_api.web.controllers;
import com.vitoria.demo_api.domain.User;
import com.vitoria.demo_api.services.UserService;
import com.vitoria.demo_api.web.controllers.dtos.UserCreateDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserResponseDTO;
import com.vitoria.demo_api.web.controllers.dtos.UserSenhaDTO;
import com.vitoria.demo_api.web.controllers.mapper.UserMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


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
