package com.example.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cont")
public class Controller {

    @Autowired
    private Repo1 repo1;

    @PostMapping("/save")
    public Entity1 save(Entity1 entity1){
        return repo1.save(entity1);
    }
}
