package com.uniguairaca.chatbot.service;

import com.uniguairaca.chatbot.model.User;
import org.springframework.stereotype.Service;
import com.uniguairaca.chatbot.dto.UserCreateDTO;
import com.uniguairaca.chatbot.repository.UserRepository;
import com.uniguairaca.chatbot.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = new User(
                userCreateDTO.name(),
                userCreateDTO.email(),
                passwordEncoder.encode(userCreateDTO.password())
        );

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserCreateDTO userCreateDTO, Long userId) {
        User user = userRepository.findOrFail(userId);

        if (!user.getEmail().equals(userCreateDTO.email()) && userRepository.existsByEmail(userCreateDTO.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        user.setName(userCreateDTO.name());
        user.setEmail(userCreateDTO.email());
        user.setPassword(passwordEncoder.encode(userCreateDTO.password()));

        return userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findOrFail(userId);
        userRepository.delete(user);
    }
}
