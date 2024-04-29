package com.example.demo.repositary;

import com.example.demo.entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterUsersRepo extends JpaRepository<MasterUser,Integer> {
    Optional<MasterUser> findByEmail(String username);

    Optional<MasterUser> findByEmailAndPassword(String email, String password);
}
