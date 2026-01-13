package com.tecin.mina.controller;

import com.tecin.mina.model.dto.AptitudDTO;
import com.tecin.mina.model.dto.ResumenAptitudDTO;
import com.tecin.mina.service.AptitudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aptitud")
@CrossOrigin(origins = "http://localhost:3000")
public class AptitudController {
    
    @Autowired
    private AptitudService aptitudService;
    
    @GetMapping("/usuario/{usuarioId}/establecimiento/{establecimientoId}")
    public ResponseEntity<AptitudDTO> verificarAptitud(
            @PathVariable Long usuarioId,
            @PathVariable Long establecimientoId) {
        return ResponseEntity.ok(aptitudService.verificarAptitud(usuarioId, establecimientoId));
    }
    
    @GetMapping("/resumen")
    public ResponseEntity<List<ResumenAptitudDTO>> obtenerResumenAptitud() {
        return ResponseEntity.ok(aptitudService.obtenerResumenAptitud());
    }
}
