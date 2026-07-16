package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.Rule;
import com.smarthome.smart_home_backend.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {

    private final RuleRepository ruleRepo;

    public RuleService(
            RuleRepository ruleRepo
    ) {
        this.ruleRepo = ruleRepo;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    public List<Rule> getAllRules() {

        return ruleRepo.findAll();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    public Rule getRuleById(Integer id) {

        return ruleRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rule not found with id: " + id
                        )
                );
    }

    /* =========================================================
       GET BY HOME
       ========================================================= */

    public List<Rule> getRulesByHome(Integer homeId) {

        return ruleRepo.findByHomeId(homeId);
    }

    /* =========================================================
       GET ACTIVE
       ========================================================= */

    public List<Rule> getActiveRules() {

        return ruleRepo.findByStatus("active");
    }

    /* =========================================================
       CREATE
       ========================================================= */

    public Rule createRule(
            Rule rule
    ) {

        return ruleRepo.save(rule);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    public Rule updateRule(
            Integer id,
            Rule updatedRule
    ) {

        Rule existingRule =
                getRuleById(id);

        existingRule.setHomeId(
                updatedRule.getHomeId()
        );

        existingRule.setRuleName(
                updatedRule.getRuleName()
        );

        existingRule.setDescription(
                updatedRule.getDescription()
        );

        existingRule.setPriority(
                updatedRule.getPriority()
        );

        existingRule.setStatus(
                updatedRule.getStatus()
        );

        return ruleRepo.save(existingRule);
    }

    /* =========================================================
       DELETE
       ========================================================= */

    public void deleteRule(
            Integer id
    ) {

        ruleRepo.deleteById(id);
    }
}