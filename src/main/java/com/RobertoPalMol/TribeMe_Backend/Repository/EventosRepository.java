package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Eventos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventosRepository extends JpaRepository<Eventos, Long> {
    List<Eventos> findByTribu_TribuId(Long tribuId);

    @Query("SELECT e FROM Eventos e LEFT JOIN FETCH e.miembrosEvento WHERE e.eventoId = :id")
    Optional<Eventos> findByIdWithMembers(@Param("id") Long id);

}
