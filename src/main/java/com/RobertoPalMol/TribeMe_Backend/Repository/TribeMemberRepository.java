package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.TribeMember;
import com.RobertoPalMol.TribeMe_Backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TribeMemberRepository  extends JpaRepository<TribeMember, Long> {
}

