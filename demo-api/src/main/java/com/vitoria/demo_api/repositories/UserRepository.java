package com.vitoria.demo_api.repositories;

import com.vitoria.demo_api.doman.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
