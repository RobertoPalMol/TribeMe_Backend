package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribe;
import com.RobertoPalMol.TribeMe_Backend.Service.TribeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tribes")
public class TribeController {
    @Autowired
    private TribeService tribeService;

    @GetMapping
    public List<Tribe> getAllTribes() {
        return tribeService.getAllTribes();
    }

    @GetMapping("/{id}")
    public Optional<Tribe> getTribeById(@PathVariable Long id) {
        return tribeService.getTribeById(id);
    }

    @PostMapping
    public Tribe createTribe(@RequestBody Tribe tribe) {
        return tribeService.createTribe(tribe);
    }

    @DeleteMapping("/{id}")
    public void deleteTribe(@PathVariable Long id) {
        tribeService.deleteTribe(id);
    }
}
