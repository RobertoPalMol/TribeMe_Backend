package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.TribeMember;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribeMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TribeMemberService {

    @Autowired
    private TribeMemberRepository tribeMemberRepository;

    public List<TribeMember> getAllTribeMembers() {
        return tribeMemberRepository.findAll();
    }

    public Optional<TribeMember> getTribeMemberById(Long id) {
        return tribeMemberRepository.findById(id);
    }

    public TribeMember addMemberToTribe(TribeMember tribeMember) {
        return tribeMemberRepository.save(tribeMember);
    }

    public Optional<TribeMember> updateTribeMember(Long id, TribeMember tribeMemberDetails) {
        Optional<TribeMember> tribeMember = tribeMemberRepository.findById(id);
        if (tribeMember.isPresent()) {
            TribeMember updatedMember = tribeMember.get();
            updatedMember.setRoll(tribeMemberDetails.getRoll());
            updatedMember.setUnionDate(tribeMemberDetails.getUnionDate());
            tribeMemberRepository.save(updatedMember);
            return Optional.of(updatedMember);
        }
        return Optional.empty();
    }

    public boolean removeMemberFromTribe(Long id) {
        if (tribeMemberRepository.existsById(id)) {
            tribeMemberRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

