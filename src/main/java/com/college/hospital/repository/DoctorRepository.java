package com.college.hospital.repository;

import com.college.hospital.entity.Doctor;
import com.college.hospital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    List<Doctor> findBySpecialization(String specialization);

    List<Doctor> findBySpecializationIgnoreCase(String specialization);
}
