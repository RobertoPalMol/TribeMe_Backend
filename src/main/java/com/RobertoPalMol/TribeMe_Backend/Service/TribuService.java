package com.RobertoPalMol.TribeMe_Backend.Service;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import com.RobertoPalMol.TribeMe_Backend.Repository.TribuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TribuService {

    private final TribuRepository tribuRepository;

    @Autowired
    public TribuService(TribuRepository tribuRepository) {
        this.tribuRepository = tribuRepository;
    }


    public Tribus obtenerPorId(Long id) {
        return tribuRepository.findById(id).orElse(null);
    }

    public Tribus guardar(Tribus tribu) {
        return tribuRepository.save(tribu);
    }

}
