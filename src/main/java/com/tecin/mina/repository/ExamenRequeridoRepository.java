package com.tecin.mina.repository;

import com.tecin.mina.model.Establecimiento;
import com.tecin.mina.model.ExamenRequerido;
import com.tecin.mina.model.TipoExamen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamenRequeridoRepository extends JpaRepository<ExamenRequerido, Long> {
    List<ExamenRequerido> findByEstablecimientoId(Long establecimientoId);
    Optional<ExamenRequerido> findByEstablecimientoAndTipoExamen(Establecimiento establecimiento, TipoExamen tipoExamen);
}
