package com.example.franconews.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@SuperBuilder
public class Journalist extends UserEntity {

    @OneToMany
    private List<News> myNews;
    private Integer monthlySalary;

    public Journalist() {

    }
}