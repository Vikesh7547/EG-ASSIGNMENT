package com.example.patientskydashoard.models;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "Appointments")
public class Appointments {
	
	@Id
	@GeneratedValue(generator="system-uuid")
@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String appointment_id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "calendar_id", nullable = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	 private UserCalender userCalender;
	
   @NotNull
    private LocalDateTime startdate;

    @NotNull
    private LocalDateTime enddate;

    @Positive
    private Long duration;
		 
		  
		  }
