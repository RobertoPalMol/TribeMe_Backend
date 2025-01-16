package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Event;
import com.RobertoPalMol.TribeMe_Backend.Entity.Tribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
