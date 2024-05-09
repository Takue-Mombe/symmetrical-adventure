package com.example.fuelsystem.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;
    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "department")
    private String department;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
