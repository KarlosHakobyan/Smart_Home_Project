package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.db.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}