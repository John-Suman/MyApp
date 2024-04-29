package com.example.demo.repositary;

import com.example.demo.entity.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsRepo extends JpaRepository<Events,Integer> {
}
