package com.example.demo.controller;

import com.example.demo.Requests.LoginDetails;
import com.example.demo.SecurityConfig.JwtService;
import com.example.demo.entity.MasterUser;
import com.example.demo.repositary.MasterUsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private MasterUsersRepo masterUsersRepo;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> add(MasterUser user){
        MasterUser user1=masterUsersRepo.save(user);
        return ResponseEntity.ok(user1);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(LoginDetails loginDetails){
        MasterUser masterUser=masterUsersRepo.findByEmailAndPassword(loginDetails.getEmail(),loginDetails.getPassword()).orElse(null);
        if(masterUser==null){
           return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jwtService.generateToken(masterUser.getId(),"master"));
    }
}
