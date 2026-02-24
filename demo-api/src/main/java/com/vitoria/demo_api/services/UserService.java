package com.vitoria.demo_api.services;


import com.vitoria.demo_api.doman.User;
import com.vitoria.demo_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
 private UserRepository userRepository;

    @Transactional
  public User salvar(User user) {
    return userRepository.save(user);
  }

  @Transactional(readOnly = true)
  public User BuscarPorId(Long id ){
        return userRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Usuario não encontrado")
        );
  }

  @Transactional
  public User EditarSenha(Long id, String password){
      User savedUser = BuscarPorId(id);
      savedUser.setPassword(password);
      return savedUser;
  }

  @Transactional(readOnly = true)
    public List<User> BuscarTodos(){
        return userRepository.findAll();

  }




}
