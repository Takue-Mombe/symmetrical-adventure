package com.example.fuelsystem.Repositories;

import com.example.fuelsystem.Models.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<Requests,Long> {
}
