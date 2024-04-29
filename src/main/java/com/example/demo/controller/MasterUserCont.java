package com.example.demo.controller;

import com.example.demo.entity.MasterUser;
import com.example.demo.repositary.MasterUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-user")
public class MasterUserCont {

    @Autowired
    private MasterUsersRepo masterUsersRepo;


    @PostMapping("/register")
    public ResponseEntity<?> add(MasterUser user){
        MasterUser user1=masterUsersRepo.save(user);
        return ResponseEntity.ok(user1);
    }
}
