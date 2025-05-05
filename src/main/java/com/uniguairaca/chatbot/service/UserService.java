package com.uniguairaca.chatbot.service;

import com.uniguairaca.chatbot.model.User;
import org.springframework.stereotype.Service;
import com.uniguairaca.chatbot.dto.UserCreateDTO;
import com.uniguairaca.chatbot.repository.UserRepository;
import com.uniguairaca.chatbot.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));
    }

    @Transactional
    public User createUser(UserCreateDTO userCreateDTO) {
        if (userRepository.existsByEmail(userCreateDTO.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        User user = new User();
        user.setName(userCreateDTO.name());
        user.setEmail(userCreateDTO.email());
        user.setPassword(passwordEncoder.encode(userCreateDTO.password()));

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserCreateDTO userCreateDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado com ID: " + userId));

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
        if (!userRepository.existsById(userId)) {
            throw new BusinessException("Usuário não encontrado com ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}