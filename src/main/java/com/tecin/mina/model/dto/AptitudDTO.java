package com.tecin.mina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AptitudDTO {
    private Long usuarioId;
    private Long establecimientoId;
    private String nombreUsuario;
    private String nombreEstablecimiento;
    private boolean apto;
    private List<ExamenAptitudDTO> examenes;
}
