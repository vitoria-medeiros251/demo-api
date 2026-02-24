package com.vitoria.demo_api.web.controllers;
import com.vitoria.demo_api.doman.User;
import com.vitoria.demo_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        User savedUser = userService.salvar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }


    @GetMapping("/{id}")
   public ResponseEntity<User> findById(@PathVariable Long id){
        User savedUser = userService.BuscarPorId(id);
        return ResponseEntity.ok(savedUser);
    }
}
