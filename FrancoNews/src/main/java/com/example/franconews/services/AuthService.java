package com.example.franconews.services;

import com.example.franconews.dto.AuthResponse;
import com.example.franconews.dto.LoginDTO;
import com.example.franconews.dto.RegisterDTO;
import com.example.franconews.entities.Admin;
import com.example.franconews.entities.Journalist;
import com.example.franconews.entities.UserEntity;
import com.example.franconews.exceptions.MyException;
import com.example.franconews.repositories.AdminRepository;
import com.example.franconews.repositories.JournalistRepository;
import com.example.franconews.repositories.UserRepository;
import com.example.franconews.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AdminRepository adminRepository;
    private final JournalistRepository journalistRepository;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, AdminRepository adminRepository, JournalistRepository journalistRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.adminRepository = adminRepository;
        this.journalistRepository = journalistRepository;
    }

    public AuthResponse login(LoginDTO data) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword()));
        UserDetails user = userRepository.findByEmail(data.getEmail()).orElseThrow();

        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(RegisterDTO data) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(data.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("There is already one user with this email");
        }

        UserEntity user = UserEntity.builder()
                .email(data.getEmail())
                .password(passwordEncoder.encode(data.getPassword()))
                .name(data.getName())
                .lastName(data.getLastName())
                .country(data.getCountry())
                .role(Role.valueOf(data.getRole()))
                .registrationDate(LocalDate.now())
                .isActive(true)
                .build();

        if (data.getRole().equalsIgnoreCase("JOURNALIST")) {
            Journalist journalist = Journalist.builder()
                    .email(data.getEmail())
                    .password(passwordEncoder.encode(data.getPassword()))
                    .name(data.getName())
                    .lastName(data.getLastName())
                    .country(data.getCountry())
                    .role(Role.valueOf(data.getRole()))
                    .registrationDate(LocalDate.now())
                    .isActive(true)
                    .build();
            journalistRepository.save(journalist);
            return AuthResponse.builder()
                    .token(jwtService.getToken(journalist))
                    .build();
        } else {
            if (data.getRole().equalsIgnoreCase("admin")) {
                Admin admin = Admin.builder()
                        .email(data.getEmail())
                        .password(passwordEncoder.encode(data.getPassword()))
                        .name(data.getName())
                        .lastName(data.getLastName())
                        .country(data.getCountry())
                        .role(Role.valueOf(data.getRole()))
                        .registrationDate(LocalDate.now())
                        .isActive(true)
                        .build();
                adminRepository.save(admin);
                return AuthResponse.builder()
                        .token(jwtService.getToken(admin))
                        .build();
            } else {
                userRepository.save(user);
                return AuthResponse.builder()
                        .token(jwtService.getToken(user))
                        .build();
            }
        }
    }
}
