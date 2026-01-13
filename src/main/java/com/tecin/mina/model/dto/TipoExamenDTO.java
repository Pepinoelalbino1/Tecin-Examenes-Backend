package com.tecin.mina.model.dto;

import com.tecin.mina.model.TipoExamen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoExamenDTO {
    private TipoExamen tipoExamen;
    private String observaciones;
}
