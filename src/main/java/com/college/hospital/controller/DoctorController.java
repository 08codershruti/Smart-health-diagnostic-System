package com.college.hospital.controller;

import com.college.hospital.entity.Doctor;
import com.college.hospital.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;
    @PostMapping("/add")
    public Doctor addDoctor(@RequestBody Doctor doctor)
    {
        return doctorService.addDoctor(doctor);
    }
    @GetMapping
    public List<Doctor> getAllDoctors() {

        return doctorService.getAllDoctors();
    }
    @PutMapping("/update/{id}")
    public Doctor updateAvailability(@PathVariable Long id,
                                     @RequestParam String availableTime) {
        return doctorService.updateAvailability(id, availableTime);
    }
}