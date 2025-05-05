package com.uniguairaca.chatbot.security.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.uniguairaca.chatbot.security.jwt.JwtService;
import com.uniguairaca.chatbot.dto.AuthenticationRequestDTO;
import com.uniguairaca.chatbot.dto.AuthenticationResponseDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ) {
        System.out.println("Request: " + request);
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        System.out.println("User authenticated: " + request.username());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String jwtToken = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponseDTO(jwtToken));
    }
}