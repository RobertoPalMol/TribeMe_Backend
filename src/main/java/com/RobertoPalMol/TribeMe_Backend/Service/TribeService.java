package com.RobertoPalMol.TribeMe_Backend.Service;


import com.RobertoPalMol.TribeMe_Backend.Entity.Tribe;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TribeService {
    @Autowired
    private TribeRepository tribeRepository;

    public List<Tribe> getAllTribes() {
        return tribeRepository.findAll();
    }

    public Optional<Tribe> getTribeById(Long id) {
        return tribeRepository.findById(id);
    }

    public Tribe createTribe(Tribe tribe) {
        return tribeRepository.save(tribe);
    }

    public void deleteTribe(Long id) {
        tribeRepository.deleteById(id);
    }
}

