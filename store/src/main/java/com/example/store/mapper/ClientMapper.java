package com.example.store.mapper;

import com.example.store.dto.ClientDTO;
import com.example.store.entities.Client;

public class ClientMapper {

    public static ClientDTO toClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setClientName(client.getClientName());
        clientDTO.setClientLastName(clientDTO.getClientLastName());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setId_client(client.getId_client());
        clientDTO.setActive(client.isActive());

        return clientDTO;
    }

    public static Client toClient(ClientDTO clientDTO) {
        Client client = new Client();

        client.setClientName(clientDTO.getClientName());
        client.setClientLastName(clientDTO.getClientLastName());
        client.setEmail(clientDTO.getEmail());
        client.setId_client(clientDTO.getId_client());
        client.setActive(clientDTO.isActive());

        return client;
    }

}
