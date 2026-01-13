package com.tecin.mina.repository;

import com.tecin.mina.model.Examen;
import com.tecin.mina.model.TipoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    List<Examen> findByUsuarioId(Long usuarioId);
    Optional<Examen> findByUsuarioIdAndTipoExamen(Long usuarioId, TipoExamen tipoExamen);
}
