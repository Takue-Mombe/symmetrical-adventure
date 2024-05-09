package com.example.fuelsystem.Repositories;

import com.example.fuelsystem.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findByEmployeeNumber(String employeeNumber);
}
