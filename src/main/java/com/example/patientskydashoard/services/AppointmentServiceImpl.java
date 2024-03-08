package com.example.patientskydashoard.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;

import com.example.patientskydashoard.repository.AppointmentsRepo;
import com.example.patientskydashoard.models.Appointments;

import java.sql.Types;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    public AppointmentsRepo appointmentrepo;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Appointments> getAppointments() {
        try {
            return appointmentrepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
           
            throw e;
        }
    }

    @Override
    public Optional<Appointments> getAppointmentById(String appId) {
        try {
            return appointmentrepo.findById(appId);
        } catch (Exception e) {
            e.printStackTrace();
           
            throw e;
        }
    }

    @Override
    public Appointments bookAppointment(Appointments appointment) {
        try {
            return appointmentrepo.save(appointment);
        } catch (Exception e) {
            e.printStackTrace();
            
            throw e;
        }
    }

    @Override
    public HashMap<String, Object> findAvailableTime(String calendarIds, Long duration, LocalDateTime startdateTime, LocalDateTime endDateTime) {
        try {
            SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withProcedureName("CALTIMEDIFF")
                    .declareParameters(
                            new SqlParameter("P_START_TIME", Types.TIMESTAMP),
                            new SqlParameter("P_END_TIME", Types.TIMESTAMP),
                            new SqlParameter("p_duration", Types.INTEGER),
                            new SqlOutParameter("p_flag", Types.VARCHAR),
                            new SqlOutParameter("p_out_msg", Types.VARCHAR)
                    );

            Map<String, Object> inParams = new HashMap<>();
            inParams.put("P_START_TIME", startdateTime);
            inParams.put("P_END_TIME", endDateTime);
            inParams.put("p_duration", duration);

            Map<String, Object> out = jdbcCall.execute(inParams);

            return (HashMap<String, Object>) out;
        } catch (Exception e) {
            e.printStackTrace();
            
            throw e;
        }
    }
}
