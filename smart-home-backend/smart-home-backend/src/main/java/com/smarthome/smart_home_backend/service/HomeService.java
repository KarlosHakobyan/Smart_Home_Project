package com.smarthome.smart_home_backend.service;

import com.smarthome.smart_home_backend.model.Home;
import com.smarthome.smart_home_backend.repository.HomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private final HomeRepository homeRepo;

    public HomeService(
            HomeRepository homeRepo
    ) {
        this.homeRepo = homeRepo;
    }

    /* =========================================================
       GET ALL
       ========================================================= */

    public List<Home> getAllHomes() {

        return homeRepo.findAll();
    }

    /* =========================================================
       GET BY ID
       ========================================================= */

    public Home getHomeById(Long id) {

        return homeRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Home not found with id: " + id
                        )
                );
    }

    /* =========================================================
       CREATE
       ========================================================= */

    public Home createHome(Home home) {

        return homeRepo.save(home);
    }

    /* =========================================================
       UPDATE
       ========================================================= */

    public Home updateHome(
            Long id,
            Home updated
    ) {

        Home home = homeRepo.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Home not found with id: " + id
                        )
                );

        home.setName(updated.getName());
        home.setAddress(updated.getAddress());
        home.setTimezone(updated.getTimezone());

        return homeRepo.save(home);
    }

    /* =========================================================
       DELETE
       ========================================================= */

    public void deleteHome(Long id) {

        homeRepo.deleteById(id);
    }
}