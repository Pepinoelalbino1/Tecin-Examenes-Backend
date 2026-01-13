package com.tecin.mina.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumenAptitudDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private String documento;
    private List<MinaAptitudDTO> minas;
}
