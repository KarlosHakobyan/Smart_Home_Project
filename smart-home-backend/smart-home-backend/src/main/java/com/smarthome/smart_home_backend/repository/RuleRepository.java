package com.smarthome.smart_home_backend.repository;

import com.smarthome.smart_home_backend.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleRepository
        extends JpaRepository<Rule, Integer> {

    List<Rule> findByHomeId(Integer homeId);

    List<Rule> findByStatus(String status);
}