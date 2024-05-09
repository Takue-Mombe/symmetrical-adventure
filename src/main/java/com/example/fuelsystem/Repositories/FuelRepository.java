package com.example.fuelsystem.Repositories;

import com.example.fuelsystem.Models.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelRepository extends JpaRepository<Fuel,Long> {
}
