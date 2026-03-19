package com.college.hospital.controller;

import com.college.hospital.entity.Appointment;
import com.college.hospital.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public Appointment bookAppointment(@RequestBody Appointment appointment) {

        return appointmentService.bookAppointment(appointment);
    }

    @GetMapping("/user/{userId}")
    public List<Appointment> getAppointmentsByUser(@PathVariable Long userId) {

        return appointmentService.getAppointmentsByUser(userId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable Long doctorId) {

        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @DeleteMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {

        return appointmentService.cancelAppointment(id);
    }
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}