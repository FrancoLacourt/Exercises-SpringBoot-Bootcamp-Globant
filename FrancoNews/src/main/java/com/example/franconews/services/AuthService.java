package com.example.franconews.services;

import com.example.franconews.dto.AuthResponse;
import com.example.franconews.dto.LoginDTO;
import com.example.franconews.dto.RegisterDTO;
import com.example.franconews.entities.User;
import com.example.franconews.repositories.UserRepository;
import com.example.franconews.roles.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginDTO data) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
        UserDetails user = userRepository.findByEmail(data.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterDTO data) {
        Optional<User> userOptional = userRepository.findByEmail(data.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("There is already one user with this email");
        }

        User user = User.builder()
                .email(data.getEmail())
                .password(passwordEncoder.encode(data.getPassword()))
                .name(data.getName())
                .lastName(data.getLastName())
                .country(data.getCountry())
                .role(Role.valueOf(data.getRole()))
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
