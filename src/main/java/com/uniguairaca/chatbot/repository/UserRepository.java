package com.uniguairaca.chatbot.repository;

import org.jetbrains.annotations.NotNull;
import com.uniguairaca.chatbot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    default User findOrFail(@NotNull Long id) {
        return findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
