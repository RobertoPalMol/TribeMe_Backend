package com.RobertoPalMol.TribeMe_Backend.Repository;

import com.RobertoPalMol.TribeMe_Backend.Entity.Tribus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface TribuRepository extends JpaRepository<Tribus, Long> {

    @Query("SELECT DISTINCT t FROM Tribus t LEFT JOIN t.categorias c " +
            "WHERE (:nombre IS NULL OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:ubicacion IS NULL OR LOWER(t.ubicacion) LIKE LOWER(CONCAT('%', :ubicacion, '%'))) " +
            "AND (:categorias IS NULL OR c.nombre IN :categorias)")
    List<Tribus> buscarTribusFiltradas(
            @Param("nombre") String nombre,
            @Param("ubicacion") String ubicacion,
            @Param("categorias") List<String> categorias);
}
