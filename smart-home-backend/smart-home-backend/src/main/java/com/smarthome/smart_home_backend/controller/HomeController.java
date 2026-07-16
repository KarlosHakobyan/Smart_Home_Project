package com.smarthome.smart_home_backend.controller;

import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.service.HomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homes")
@CrossOrigin(origins = "*")
public class HomeController {

    private final HomeService homeService;

    public HomeController(
            HomeService homeService
    ) {
        this.homeService = homeService;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    @GetMapping
    public List<Home> getAllHomes() {

        return homeService.getAllHomes();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    @GetMapping("/{id}")
    public Home getHomeById(
            @PathVariable Long id
    ) {

        return homeService.getHomeById(id);
    }

    /* =========================================================
       CREATE
       ========================================================= */

    @PostMapping
    public Home createHome(
            @RequestBody Home home
    ) {

        return homeService.createHome(home);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    @PutMapping("/{id}")
    public Home updateHome(
            @PathVariable Long id,
            @RequestBody Home updated
    ) {

        return homeService.updateHome(
                id,
                updated
        );
    }

    /* =========================================================
       DELETE
       ========================================================= */

    @DeleteMapping("/{id}")
    public void deleteHome(
            @PathVariable Long id
    ) {

        homeService.deleteHome(id);
    }
}