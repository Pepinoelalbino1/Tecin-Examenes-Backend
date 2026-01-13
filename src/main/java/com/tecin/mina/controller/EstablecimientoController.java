package com.tecin.mina.controller;

import com.tecin.mina.model.dto.EstablecimientoDTO;
import com.tecin.mina.service.EstablecimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/establecimientos")
@CrossOrigin(origins = "http://localhost:3000")
public class EstablecimientoController {
    
    @Autowired
    private EstablecimientoService establecimientoService;
    
    @GetMapping
    public ResponseEntity<List<EstablecimientoDTO>> findAll() {
        return ResponseEntity.ok(establecimientoService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EstablecimientoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(establecimientoService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<EstablecimientoDTO> create(@Valid @RequestBody EstablecimientoDTO establecimientoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(establecimientoService.create(establecimientoDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EstablecimientoDTO> update(@PathVariable Long id, @Valid @RequestBody EstablecimientoDTO establecimientoDTO) {
        return ResponseEntity.ok(establecimientoService.update(id, establecimientoDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        establecimientoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
