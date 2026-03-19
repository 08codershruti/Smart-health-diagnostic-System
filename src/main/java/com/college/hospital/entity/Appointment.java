package com.college.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "appointments")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long doctorId;

    private String appointmentDate;

    private String appointmentTime;

    private String status;
}