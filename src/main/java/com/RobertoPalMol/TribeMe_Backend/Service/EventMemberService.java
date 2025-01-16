package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.EventMember;
import com.RobertoPalMol.TribeMe_Backend.Repository.EventMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventMemberService {

    @Autowired
    private EventMemberRepository eventMemberRepository;

    public List<EventMember> getAllEventMembers() {
        return eventMemberRepository.findAll();
    }

    public Optional<EventMember> getEventMemberById(Long id) {
        return eventMemberRepository.findById(id);
    }

    public EventMember addMemberToEvent(EventMember eventMember) {
        return eventMemberRepository.save(eventMember);
    }

    public boolean removeMemberFromEvent(Long id) {
        if (eventMemberRepository.existsById(id)) {
            eventMemberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
