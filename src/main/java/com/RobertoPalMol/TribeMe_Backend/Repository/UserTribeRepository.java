package com.RobertoPalMol.TribeMe_Backend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.RobertoPalMol.TribeMe_Backend.Entity.UserTribe;

public interface UserTribeRepository extends JpaRepository<UserTribe, UserTribe.UserTribeId> {
}

