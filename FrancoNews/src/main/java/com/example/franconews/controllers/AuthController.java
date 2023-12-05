package com.example.franconews.controllers;

import com.example.franconews.dto.AuthResponse;
import com.example.franconews.dto.LoginDTO;
import com.example.franconews.dto.RegisterDTO;
import com.example.franconews.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDTO data) {

        try {
            return ResponseEntity.ok(authService.login(data));
        } catch (RuntimeException e){

            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDTO data) {

        try {
            return ResponseEntity.ok(authService.register(data));
        } catch (RuntimeException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
