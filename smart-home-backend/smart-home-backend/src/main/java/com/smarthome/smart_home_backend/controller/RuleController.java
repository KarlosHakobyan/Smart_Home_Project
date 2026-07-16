package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.model.Rule;
import com.smarthome.smart_home_backend.service.RuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
@CrossOrigin(origins = "*")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(
            RuleService ruleService
    ) {
        this.ruleService = ruleService;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    @GetMapping
    public List<Rule> getAllRules() {

        return ruleService.getAllRules();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    @GetMapping("/{id}")
    public Rule getRuleById(
            @PathVariable Integer id
    ) {

        return ruleService.getRuleById(id);
    }

    /* =========================================================
       GET BY HOME
       ========================================================= */

    @GetMapping("/home/{homeId}")
    public List<Rule> getRulesByHome(
            @PathVariable Integer homeId
    ) {

        return ruleService.getRulesByHome(homeId);
    }

    /* =========================================================
       CREATE
       ========================================================= */

    @PostMapping
    public Rule createRule(
            @RequestBody Rule rule
    ) {

        return ruleService.createRule(rule);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    @PutMapping("/{id}")
    public Rule updateRule(
            @PathVariable Integer id,
            @RequestBody Rule rule
    ) {

        return ruleService.updateRule(
                id,
                rule
        );
    }

    /* =========================================================
       DELETE
       ========================================================= */

    @DeleteMapping("/{id}")
    public void deleteRule(
            @PathVariable Integer id
    ) {

        ruleService.deleteRule(id);
    }
}