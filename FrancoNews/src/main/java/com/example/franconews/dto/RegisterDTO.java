package com.example.franconews.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {

    String email;
    String password;
    String name;
    String lastName;
    String country;
    String role;

}