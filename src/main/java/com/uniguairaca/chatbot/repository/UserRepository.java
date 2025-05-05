package com.uniguairaca.chatbot.repository;

import com.uniguairaca.chatbot.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findOrFail(Long id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
    }

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
