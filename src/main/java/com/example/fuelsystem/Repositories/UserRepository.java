package com.example.fuelsystem.Repositories;

import com.example.fuelsystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT c FROM users c WHERE LOWER(c.employee) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> search(@Param("searchTerm") String searchTerm);
    User findByUsername(String accountNumber);

}
