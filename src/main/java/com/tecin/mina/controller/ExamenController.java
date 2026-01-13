package com.tecin.mina.controller;

import com.tecin.mina.model.dto.ExamenDTO;
import com.tecin.mina.service.ExamenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/examenes")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamenController {
    
    @Autowired
    private ExamenService examenService;
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ExamenDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(examenService.findByUsuarioId(usuarioId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ExamenDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(examenService.findById(id));
    }
    
    @PostMapping
    public ResponseEntity<ExamenDTO> create(@Valid @RequestBody ExamenDTO examenDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(examenService.create(examenDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ExamenDTO> update(@PathVariable Long id, @Valid @RequestBody ExamenDTO examenDTO) {
        return ResponseEntity.ok(examenService.update(id, examenDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        examenService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
