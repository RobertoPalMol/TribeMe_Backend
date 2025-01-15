package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TribeRepository extends JpaRepository<Tribe, Long> {
}

