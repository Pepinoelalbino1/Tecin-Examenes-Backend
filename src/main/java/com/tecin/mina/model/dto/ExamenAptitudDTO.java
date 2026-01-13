package com.tecin.mina.model.dto;

import com.tecin.mina.model.EstadoExamen;
import com.tecin.mina.model.TipoExamen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenAptitudDTO {
    private TipoExamen tipoExamen;
    private EstadoExamen estado;
    private boolean requerido;
    private boolean presente;
}
