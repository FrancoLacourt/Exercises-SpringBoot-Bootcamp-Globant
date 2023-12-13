package com.example.franconews.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@SuperBuilder
public class Admin extends UserEntity {

    public Admin() {

    }
}
