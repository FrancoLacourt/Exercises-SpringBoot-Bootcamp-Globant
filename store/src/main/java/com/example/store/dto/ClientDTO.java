package com.example.store.dto;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDTO {

    private Long id_client;
    private String clientName;
    private String clientLastName;
    private String email;
    private Long phoneNumber;
    private boolean isActive = true;

    public ClientDTO(Long id_client, String clientName, String clientLastName, String email, boolean isActive) {
        this.id_client = id_client;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.email = email;
        this.isActive = isActive;
    }
}


