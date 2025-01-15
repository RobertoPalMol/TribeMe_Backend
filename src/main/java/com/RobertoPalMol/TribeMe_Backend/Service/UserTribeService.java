package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.UserTribe;
import com.RobertoPalMol.TribeMe_Backend.Repository.UserTribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTribeService {
    @Autowired
    private UserTribeRepository userTribeRepository;

    public List<UserTribe> getAllMemberships() {
        return userTribeRepository.findAll();
    }

    public UserTribe addMembership(UserTribe userTribe) {
        return userTribeRepository.save(userTribe);
    }
}
