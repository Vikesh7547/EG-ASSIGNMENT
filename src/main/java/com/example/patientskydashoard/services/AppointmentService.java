package com.example.patientskydashoard.services;

import com.example.patientskydashoard.models.Appointments;

import java.time.LocalDateTime;
import java.util.*;

public interface AppointmentService {
    
    //create

    //Get
    public List<Appointments> getAppointments();
  
    public java.util.Optional<Appointments> getAppointmentById(String appId);

    public Appointments bookAppointment(Appointments appointment);
     public HashMap<String, Object> findAvailableTime (String calendarIds, Long duration, LocalDateTime startdateTime, LocalDateTime endDateTime);
    }    
