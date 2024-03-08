package com.example.patientskydashoard.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "UserCalender")

public class UserCalender {
	
	@Id
	
	 private String calendar_id;
   @NotBlank
    @Size(max = 255) // Adjust the size constraint based on your requirements
    private String username;
	   
		  
		  }
