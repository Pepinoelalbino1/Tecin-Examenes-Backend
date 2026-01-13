package com.tecin.mina.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, unique = true, length = 50)
    private String documento;
    
    @Column(length = 100)
    private String email;
    
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Examen> examenes = new ArrayList<>();
    
    public void addExamen(Examen examen) {
        examenes.add(examen);
        examen.setUsuario(this);
    }
    
    public void removeExamen(Examen examen) {
        examenes.remove(examen);
        examen.setUsuario(null);
    }
}
