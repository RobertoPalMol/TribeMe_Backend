package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribe;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tribes")
public class TribeController {

    @Autowired
    private TribeRepository tribeRepository;

    @GetMapping
    public List<Tribe> getAllTribes() {
        return tribeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tribe> getTribeById(@PathVariable Long id) {
        Optional<Tribe> tribe = tribeRepository.findById(id);
        return tribe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tribe> createTribe(@RequestBody Tribe tribe) {
        Tribe savedTribe = tribeRepository.save(tribe);
        return new ResponseEntity<>(savedTribe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tribe> updateTribe(@PathVariable Long id, @RequestBody Tribe tribeDetails) {
        Optional<Tribe> tribe = tribeRepository.findById(id);
        if (tribe.isPresent()) {
            Tribe updatedTribe = tribe.get();
            updatedTribe.setName(tribeDetails.getName());
            updatedTribe.setDescription(tribeDetails.getDescription());
            updatedTribe.setMaxMembers(tribeDetails.getMaxMembers());
            updatedTribe.setPhoto(tribeDetails.getPhoto());
            updatedTribe.setPrivateTribe(tribeDetails.getPrivateTribe());
            updatedTribe.setPrivateEvent(tribeDetails.getPrivateEvent());
            updatedTribe.setCreationTime(tribeDetails.getCreationTime());
            tribeRepository.save(updatedTribe);
            return ResponseEntity.ok(updatedTribe);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTribe(@PathVariable Long id) {
        if (tribeRepository.existsById(id)) {
            tribeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
