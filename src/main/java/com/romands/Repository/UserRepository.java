package com.romands.Repository;

import com.romands.Entity.User; // <-- Esse aqui, não o do Spring Security
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String email);
    User findUserById(Long id);
}
