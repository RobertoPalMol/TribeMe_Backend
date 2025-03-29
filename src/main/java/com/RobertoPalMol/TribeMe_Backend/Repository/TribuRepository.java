package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TribuRepository extends JpaRepository<Tribus, Long> {
}
