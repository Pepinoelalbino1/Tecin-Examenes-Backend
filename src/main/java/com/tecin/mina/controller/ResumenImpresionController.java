package com.tecin.mina.controller;

import com.tecin.mina.model.dto.ResumenImpresionDTO;
import com.tecin.mina.service.ResumenImpresionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resumen")
@CrossOrigin(origins = "http://localhost:3000")
public class ResumenImpresionController {
    
    @Autowired
    private ResumenImpresionService resumenImpresionService;
    
    /**
     * Obtiene el resumen de un usuario específico con sus exámenes
     * @param usuarioId ID del usuario
     * @return ResumenImpresionDTO con los datos del usuario y sus exámenes
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<ResumenImpresionDTO> obtenerResumenUsuario(@PathVariable Long usuarioId) {
        ResumenImpresionDTO resumen = resumenImpresionService.generarResumenUsuario(usuarioId);
        return ResponseEntity.ok(resumen);
    }
    
    /**
     * Obtiene el resumen general de todos los usuarios
     * @return ResumenImpresionDTO con todos los usuarios y sus exámenes
     */
    @GetMapping("/usuarios")
    public ResponseEntity<ResumenImpresionDTO> obtenerResumenTodosUsuarios() {
        ResumenImpresionDTO resumen = resumenImpresionService.generarResumenTodosUsuarios();
        return ResponseEntity.ok(resumen);
    }
    
    /**
     * Obtiene el resumen general de establecimientos
     * @return ResumenImpresionDTO con todos los establecimientos y sus exámenes requeridos
     */
    @GetMapping("/establecimientos")
    public ResponseEntity<ResumenImpresionDTO> obtenerResumenEstablecimientos() {
        ResumenImpresionDTO resumen = resumenImpresionService.generarResumenEstablecimientos();
        return ResponseEntity.ok(resumen);
    }
    
    /**
     * Obtiene el resumen de un establecimiento específico
     * @param establecimientoId ID del establecimiento
     * @return ResumenImpresionDTO con los datos del establecimiento y sus exámenes requeridos
     */
    @GetMapping("/establecimiento/{establecimientoId}")
    public ResponseEntity<ResumenImpresionDTO> obtenerResumenEstablecimiento(@PathVariable Long establecimientoId) {
        ResumenImpresionDTO resumen = resumenImpresionService.generarResumenEstablecimiento(establecimientoId);
        return ResponseEntity.ok(resumen);
    }
}
