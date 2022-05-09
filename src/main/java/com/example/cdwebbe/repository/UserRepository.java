package com.example.cdwebbe.repository;

import com.example.cdwebbe.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
