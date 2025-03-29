package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Entity.Usuarios;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tribus")
public class TribusController {

    @Autowired
    private TribuRepository tribusController;

    @GetMapping
    public List<Tribus> getAllTribus() {
        return tribusController.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tribus> getTribuById(@PathVariable Long id) {
        Optional<Tribus> user = tribusController.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tribus> createTribu(@RequestBody Tribus tribus) {
        Tribus savedTribus = tribusController.save(tribus);
        return new ResponseEntity<>(savedTribus, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTribu(@PathVariable Long id) {
        if (tribusController.existsById(id)) {
            tribusController.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

