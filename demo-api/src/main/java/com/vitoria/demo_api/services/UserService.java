package com.vitoria.demo_api.services;
import com.vitoria.demo_api.domain.User;
import com.vitoria.demo_api.exception.EntityNotFoundException;
import com.vitoria.demo_api.exception.PasswordInvalidException;
import com.vitoria.demo_api.exception.UsernameUniqueViolationException;
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
        try {
            return userRepository.save(user);

        }catch(org.springframework.dao.DataIntegrityViolationException ex) {
            throw new UsernameUniqueViolationException(String.format("Username '%s' ja cadastrado", user.getUsername()));
        }
    }



  @Transactional(readOnly = true)
  public User BuscarPorId(Long id ){
        return userRepository.findById(id).orElseThrow(
                ()-> new EntityNotFoundException(String.format("Usuario id '%s' não encontrado",id))
        );
  }

  @Transactional
  public User EditarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha){
        if(!novaSenha.equals(confirmaSenha)){
            throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha");
        }
        User savedUser = BuscarPorId(id);
        if(!savedUser.getPassword().equals(senhaAtual)) {
            throw new PasswordInvalidException("Senha atual incorreta");
        }
        savedUser.setPassword(novaSenha);
        return userRepository.save(savedUser);
  }

  @Transactional(readOnly = true)
    public List<User> BuscarTodos(){
        return userRepository.findAll();

  }




}
