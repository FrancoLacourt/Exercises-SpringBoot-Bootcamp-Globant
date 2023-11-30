package com.example.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_client;

    private String clientName;
    private String clientLastName;
    private String email;
    private Long phoneNumber;
    private boolean isActive = true;
}