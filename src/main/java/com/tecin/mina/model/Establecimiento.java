package com.tecin.mina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "establecimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Establecimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 200)
    private String descripcion;
    
    @Column(length = 100)
    private String ubicacion;
    
    @OneToMany(mappedBy = "establecimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamenRequerido> examenesRequeridos = new ArrayList<>();
    
    public void addExamenRequerido(ExamenRequerido examenRequerido) {
        examenesRequeridos.add(examenRequerido);
        examenRequerido.setEstablecimiento(this);
    }
    
    public void removeExamenRequerido(ExamenRequerido examenRequerido) {
        examenesRequeridos.remove(examenRequerido);
        examenRequerido.setEstablecimiento(null);
    }
}
