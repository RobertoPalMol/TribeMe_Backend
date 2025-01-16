package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.Entity.TribeMember;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tribe-members")
public class TribeMemberController {

    @Autowired
    private TribeMemberRepository tribeMemberRepository;

    @GetMapping
    public List<TribeMember> getAllTribeMembers() {
        return tribeMemberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TribeMember> getTribeMemberById(@PathVariable Long id) {
        Optional<TribeMember> tribeMember = tribeMemberRepository.findById(id);
        return tribeMember.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TribeMember> addMemberToTribe(@RequestBody TribeMember tribeMember) {
        TribeMember savedMember = tribeMemberRepository.save(tribeMember);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TribeMember> updateTribeMember(@PathVariable Long id, @RequestBody TribeMember tribeMemberDetails) {
        Optional<TribeMember> tribeMember = tribeMemberRepository.findById(id);
        if (tribeMember.isPresent()) {
            TribeMember updatedMember = tribeMember.get();
            updatedMember.setRoll(tribeMemberDetails.getRoll());
            updatedMember.setUnionDate(tribeMemberDetails.getUnionDate());
            tribeMemberRepository.save(updatedMember);
            return ResponseEntity.ok(updatedMember);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMemberFromTribe(@PathVariable Long id) {
        if (tribeMemberRepository.existsById(id)) {
            tribeMemberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
