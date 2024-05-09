package com.example.fuelsystem.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter@Setter
@AllArgsConstructor@NoArgsConstructor
public class Requests {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double requestQuantity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private RequestStatus requestStatus;
}
