package com.example.patientskydashoard.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.patientskydashoard.exceptions.ResourceNotFoundException;
import com.example.patientskydashoard.models.UserCalender;
import com.example.patientskydashoard.repository.UsercalenderRepo;

@RestController
@RequestMapping("/api/v1")
public class UsercalenderControler {

    @Autowired
    private UsercalenderRepo userCalenderRepository;

    @GetMapping("/usercal")
    public ResponseEntity<List<UserCalender>> getUsers() {
        try {
            List<UserCalender> users = userCalenderRepository.findAll();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @GetMapping("/usercal/{id}")
    public ResponseEntity<UserCalender> getUserCalenderById(
            @PathVariable(value = "id") String calid) {
        try {
            UserCalender user = userCalenderRepository.findById(calid)
                    .orElseThrow(() -> new ResourceNotFoundException("Cal not found :: " + calid));
            return ResponseEntity.ok().body(user);
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    @PostMapping("/usercal")
    public ResponseEntity<UserCalender> createUserCal(@Valid @RequestBody UserCalender usercalender) {
        try {
            UserCalender savedUser = userCalenderRepository.save(usercalender);
            return ResponseEntity.ok().body(savedUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build(); // Internal Server Error
        }
    }

    
}
