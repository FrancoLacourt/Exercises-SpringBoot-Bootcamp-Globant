package com.example.franconews.controllers;

import com.example.franconews.dto.JournalistDTO;
import com.example.franconews.entities.Journalist;
import com.example.franconews.services.JournalistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journalist")
@RequiredArgsConstructor
public class JournalistController extends AuthController {

    @Autowired
    private JournalistService journalistService;

    @PutMapping("/activate/{id}")
    @Secured({"ADMIN"})
    public ResponseEntity<JournalistDTO> activateJournalist(@PathVariable Long id) {

        Journalist journalist = journalistService.findJournalistById(id);

        if (journalist != null) {
            JournalistDTO activatedJournalist = journalistService.activateJournalist(id);

            return ResponseEntity.status(HttpStatus.OK).body(activatedJournalist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/deactivate/{id}")
    @Secured({"ADMIN"})
    public ResponseEntity<JournalistDTO> deactivateJournalist(@PathVariable Long id) {

        Journalist journalist = journalistService.findJournalistById(id);

        if (journalist != null) {
            JournalistDTO deactivatedJournalist = journalistService.deactivateJournalist(id);

            return ResponseEntity.status(HttpStatus.OK).body(deactivatedJournalist);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/monthlySalary/{id}")
    @Secured({"ADMIN"})
    public ResponseEntity<JournalistDTO> setMonthlySalary(@PathVariable Long id, @RequestParam Integer monthlySalary) {

        Journalist journalist = journalistService.findJournalistById(id);

        if (journalist != null) {
            JournalistDTO journalistDTO = journalistService.setMonthlySalary(id, monthlySalary);

            return ResponseEntity.status(HttpStatus.OK).body(journalistDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
}

