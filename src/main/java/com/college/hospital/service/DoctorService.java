package com.college.hospital.service;

import com.college.hospital.entity.Doctor;
import com.college.hospital.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    public Doctor addDoctor(Doctor doctor)
    {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    public Doctor updateAvailability(Long id, String availableTime) {
        Doctor doctor = doctorRepository.findById(id).orElse(null);

        if (doctor != null) {
            doctor.setAvailableTime(availableTime);
            return doctorRepository.save(doctor);
        }

        return null;
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }

    public String deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
        return "Doctor deleted successfully";
    }
}
