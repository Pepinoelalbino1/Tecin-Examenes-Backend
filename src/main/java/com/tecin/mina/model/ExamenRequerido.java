package com.tecin.mina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examenes_requeridos", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"establecimiento_id", "tipo_examen"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenRequerido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "establecimiento_id", nullable = false)
    private Establecimiento establecimiento;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_examen", nullable = false, length = 50)
    private TipoExamen tipoExamen;
    
    @Column(length = 500)
    private String observaciones;
}
