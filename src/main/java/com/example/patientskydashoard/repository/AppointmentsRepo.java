package com.example.patientskydashoard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.example.patientskydashoard.models.Appointments;


@Repository
public interface AppointmentsRepo extends JpaRepository<Appointments, String>{
    
    @Query(value = "SELECT * FROM Appointments WHERE CALENDAR_ID = ?1", nativeQuery = true)
    List<Appointments> findTimeByCalenderId(String calid);
    
    
}