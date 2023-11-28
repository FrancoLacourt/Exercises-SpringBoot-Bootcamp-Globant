package com.example.store.services;

import com.example.store.dto.ClientDTO;
import com.example.store.entities.Client;
import com.example.store.exceptions.MyException;
import com.example.store.mapper.ClientMapper;
import com.example.store.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientDTO createClient(ClientDTO clientDTO) throws MyException {
        validate(clientDTO);
        Client client = ClientMapper.toClient(clientDTO);
        Client savedClient = clientRepository.save(client);

        return ClientMapper.toClientDTO(savedClient);
    }

    public List<ClientDTO> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();

        for (Client client : clients) {
            ClientDTO clientDTO = ClientMapper.toClientDTO(client);
            clientsDTO.add(clientDTO);
        }

        return clientsDTO;
    }

    public List<ClientDTO> getActiveClients() {
        List<ClientDTO> clientsDTO = getAllClients();

        return clientsDTO.stream().filter(ClientDTO::isActive).collect(Collectors.toList());
    }

    public ClientDTO findClientById(long id_client) {

        Client client = clientRepository.findById(id_client).orElse(null);

        if (client != null) {
            return ClientMapper.toClientDTO(client);
        } else {
            System.out.println("It wasn´t possible to find a client with the ID: " + id_client);
            return null;
        }
    }

    public ClientDTO findClientByEmail(String email) {
        Client client = clientRepository.findByEmailAndIsActiveIsTrue(email);

        if (client != null) {
            return ClientMapper.toClientDTO(client);
        } else {
            System.out.println("It wasn´t possible to find a client with email: " + email);
            return null;
        }
    }

    public ClientDTO updateClient(Long id_client, ClientDTO updatedClientDTO) throws MyException {

        validate(updatedClientDTO);

        Client client = clientRepository.findById(id_client).orElse(null);

        if (client != null) {

            client.setClientName(updatedClientDTO.getClientName());
            client.setClientLastName(updatedClientDTO.getClientLastName());
            client.setEmail(updatedClientDTO.getEmail());

            Client savedClient = clientRepository.save(client);

            return ClientMapper.toClientDTO(savedClient);
        } else {
            System.out.println("It wasn't possible to find a client with the ID: " + id_client);
            return null;
        }
    }

    public void deactivateClient(Long id_client) {

        Client client = clientRepository.findById(id_client).orElse(null);

        if (client != null) {
            client.setActive(false);
            Client savedClient = clientRepository.save(client);
        } else {
            System.out.println("It wasn´t possible to find");
        }

    }



    public boolean onlySpaces(String input) {
        return input.trim().isEmpty();
    }

    private void validate(ClientDTO clientDTO) throws MyException {

        if (clientDTO.getClientName() == null || onlySpaces(clientDTO.getClientName())) {
            throw new MyException("Client's name can't be null or empty");
        }

        if (clientDTO.getClientLastName() == null || onlySpaces(clientDTO.getClientLastName())) {
            throw new MyException("Client's last name can't be null or empty");
        }

        if (clientDTO.getEmail() == null || onlySpaces(clientDTO.getEmail())) {
            throw new MyException("Client's email can't be null or empty");
        }

        if (clientDTO.getPhoneNumber() == null) {
            throw new MyException("Client's phone number can't be null or empty");
        }
    }
}