package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDAO extends JpaRepository<Admin , Long> {
    Admin findByEmail(String email);
}
