package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.db.Entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionRepository extends JpaRepository<Action, Integer> {
}