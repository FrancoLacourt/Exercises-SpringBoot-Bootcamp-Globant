package com.example.franconews.dto;

import com.example.franconews.entities.News;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JournalistDTO {

    String email;
    String password;
    String name;
    String lastName;
    String country;
    String role;
    ArrayList<News> myNews;
    Integer monthlySalary;
    boolean isActive;

}
