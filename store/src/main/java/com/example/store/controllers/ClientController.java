package com.example.store.controllers;

import com.example.store.dto.ClientDTO;
import com.example.store.entities.Client;
import com.example.store.exceptions.MyException;
import com.example.store.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.IllegalFormatCodePointException;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> register(@RequestBody ClientDTO clientDTO) {

        try {
            ClientDTO savedClientDTO = clientService.createClient(clientDTO);


            if (clientDTO.getClientName() == null || clientDTO.getClientLastName() == null
            || clientDTO.getEmail() == null || clientDTO.getPhoneNumber() == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(savedClientDTO);

        } catch (MyException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/listOfClients")
    public ResponseEntity<List<ClientDTO>> getClients() {
        List<ClientDTO> clientsDTO = clientService.getActiveClients();

        if (clientsDTO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(clientsDTO);
    }

    @GetMapping("/{id_client}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id_client) {
        ClientDTO clientDTO = clientService.findClientById(id_client);

        if (clientDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/findByEmail")
    public ResponseEntity<ClientDTO> findByEmail(@RequestParam String email) {
        ClientDTO clientDTO = clientService.findClientByEmail(email);

        if (clientDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/update/{id_client}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id_client, @RequestBody ClientDTO updatedClientDTO) throws MyException{

        ClientDTO clientDTO = clientService.updateClient(id_client, updatedClientDTO);

        if (updatedClientDTO.getClientName() == null || updatedClientDTO.getClientLastName() == null
                || updatedClientDTO.getEmail() == null || updatedClientDTO.getPhoneNumber() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        if (clientDTO.getId_client() == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @PutMapping("/deactivate/{id_client}")
    public ResponseEntity<ClientDTO> deactivateClient(@PathVariable Long id_client) throws MyException {

        ClientDTO clientDTO = clientService.findClientById(id_client);

        if (clientDTO != null) {
           ClientDTO deactivatedClientDTO = clientService.deactivateClient(id_client);
            return ResponseEntity.status(HttpStatus.OK).body(deactivatedClientDTO);
        } else {
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(null);
        }
    }
}
