package com.tecin.mina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MinaAptitudDTO {
    private Long establecimientoId;
    private String nombreEstablecimiento;
    private boolean apto;
}
