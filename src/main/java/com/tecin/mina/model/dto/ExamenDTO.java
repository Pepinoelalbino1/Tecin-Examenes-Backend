package com.tecin.mina.model.dto;

import com.tecin.mina.model.EstadoExamen;
import com.tecin.mina.model.TipoExamen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenDTO {
    private Long id;
    private Long usuarioId;
    private TipoExamen tipoExamen;
    private LocalDate fechaEmision;
    private LocalDate fechaCaducidad;
    private String observaciones;
    private EstadoExamen estado;
}
