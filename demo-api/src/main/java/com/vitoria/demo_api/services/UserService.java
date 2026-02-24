package com.vitoria.demo_api.services;


import com.vitoria.demo_api.doman.User;
import com.vitoria.demo_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
