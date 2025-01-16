package com.RobertoPalMol.TribeMe_Backend.Controller;

import com.RobertoPalMol.TribeMe_Backend.Entity.EventMember;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event-members")
public class EventMemberController {

    @Autowired
    private EventMemberRepository eventMemberRepository;

    @GetMapping
    public List<EventMember> getAllEventMembers() {
        return eventMemberRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventMember> getEventMemberById(@PathVariable Long id) {
        Optional<EventMember> eventMember = eventMemberRepository.findById(id);
        return eventMember.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventMember> addMemberToEvent(@RequestBody EventMember eventMember) {
        EventMember savedMember = eventMemberRepository.save(eventMember);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMemberFromEvent(@PathVariable Long id) {
        if (eventMemberRepository.existsById(id)) {
            eventMemberRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}


