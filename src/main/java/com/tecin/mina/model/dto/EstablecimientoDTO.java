package com.tecin.mina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstablecimientoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private List<TipoExamenDTO> examenesRequeridos;
}
