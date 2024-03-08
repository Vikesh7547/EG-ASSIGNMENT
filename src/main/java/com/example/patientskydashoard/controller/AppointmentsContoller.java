package com.example.patientskydashoard.controller;

import java.util.*;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.patientskydashoard.exceptions.ResourceNotFoundException;
import com.example.patientskydashoard.models.Appointments;
import com.example.patientskydashoard.repository.AppointmentsRepo;
import com.example.patientskydashoard.repository.UsercalenderRepo;
import com.example.patientskydashoard.models.UserCalender;
import com.example.patientskydashoard.services.AppointmentService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
public class AppointmentsContoller {

    @Autowired
    public AppointmentService appointmentservice;
    @Autowired
    public AppointmentsRepo appointmentRepository;
    @Autowired
    private UsercalenderRepo userCalrepo;

    @GetMapping("/appointments")
    public List<Appointments> getAppointments() {
        try {
            return appointmentRepository.findAll();
        } catch (Exception e) {
            // Handle exception (e.g., log it or return an error response)
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @GetMapping("/getappointments/{calId}")
public ResponseEntity<List<Appointments>> getAppointmentByCalenderId(
        @PathVariable(value = "calId") String calId) {
    try {
        System.out.println("calender id is" + calId);
        List<Appointments> appointments = appointmentRepository.findTimeByCalenderId(calId);
        System.out.println("appointments is" + appointments);
        return ResponseEntity.ok(appointments);
    } catch (Exception e) {
        // Handle exception (e.g., log it or return an error response)
        e.printStackTrace();
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PostMapping("/appointments/{calid}")
    public ResponseEntity<String> bookAppointment(
            @Valid @RequestBody Appointments appointment,
            @PathVariable(value = "calid") String calid) {
        try {
            System.out.println("appointment22 " + appointment.getStartdate());
    
            Optional<UserCalender> user = userCalrepo.findById(calid);
            if (user != null) {
                appointment.setUserCalender(user.get());
    
                // Save the appointment
                Appointments savedAppointment = appointmentRepository.save(appointment);
    
                // Check if the save operation was successful
                if (savedAppointment != null) {
                    return new ResponseEntity<>("Appointment saved successfully", HttpStatus.CREATED);
                } else {
                    return new ResponseEntity<>("Failed to save appointment", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("Calender Doesnot Exist", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            // Handle exception (e.g., log it or return an error response)
            e.printStackTrace();
            return new ResponseEntity<>("Unexpected error.Please try after somtime", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    


    @GetMapping("/availability")
    public ResponseEntity<String> findAvailableSlots(
            @RequestParam String calendarIds,
            @RequestParam Long duration,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateTime) {
        try {
            HashMap<String, Object> res = appointmentservice.findAvailableTime(calendarIds, duration, startDateTime, endDateTime);
            return new ResponseEntity<>((String) res.get("p_out_msg"), HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception (e.g., log it or return an error response)
            e.printStackTrace();
            return new ResponseEntity<>("Unexpected error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
