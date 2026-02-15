package com.example.SanGeets.DAO;

import com.example.SanGeets.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
