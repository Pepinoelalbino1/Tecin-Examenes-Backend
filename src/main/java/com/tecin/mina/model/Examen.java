package com.tecin.mina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "examenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoExamen tipoExamen;
    
    @Column(nullable = false)
    private LocalDate fechaEmision;
    
    @Column(nullable = false)
    private LocalDate fechaCaducidad;
    
    @Column(length = 500)
    private String observaciones;
    
    @Transient
    private EstadoExamen estado;
    
    @PostLoad
    @PostUpdate
    @PostPersist
    public void calcularEstado() {
        LocalDate hoy = LocalDate.now();
        LocalDate fechaLimite = fechaCaducidad.minusDays(7);
        
        if (hoy.isAfter(fechaCaducidad)) {
            estado = EstadoExamen.VENCIDO;
        } else if (hoy.isAfter(fechaLimite) || hoy.isEqual(fechaLimite)) {
            estado = EstadoExamen.POR_VENCER;
        } else {
            estado = EstadoExamen.VIGENTE;
        }
    }
}
