package com.college.hospital.service;

import com.college.hospital.entity.Appointment;
import com.college.hospital.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Appointment bookAppointment(Appointment appointment) {

        appointment.setStatus("BOOKED");

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByUser(Long userId) {

        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {

        return appointmentRepository.findByDoctorId(doctorId);
    }

    public String cancelAppointment(Long id) {

        appointmentRepository.deleteById(id);

        return "Appointment cancelled successfully";
    }

    public List<Appointment> getAllAppointments() {
       return appointmentRepository.findAll();
    }
}