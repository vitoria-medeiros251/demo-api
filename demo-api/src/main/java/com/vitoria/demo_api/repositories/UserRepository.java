package com.vitoria.demo_api.repositories;

import com.vitoria.demo_api.doman.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long aLong);
}
