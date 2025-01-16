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

    public Optional<Tribe> updateTribe(Long id, Tribe tribeDetails) {
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
            return Optional.of(updatedTribe);
        }
        return Optional.empty();
    }

    public boolean deleteTribe(Long id) {
        if (tribeRepository.existsById(id)) {
            tribeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}


