package com.example.patientskydashoard.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.patientskydashoard.models.UserCalender;

import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Repository
public interface UsercalenderRepo extends JpaRepository<UserCalender, String>{
    

}