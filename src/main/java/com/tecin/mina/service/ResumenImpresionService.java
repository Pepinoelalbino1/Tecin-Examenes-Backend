package com.tecin.mina.service;

import com.tecin.mina.model.Examen;
import com.tecin.mina.model.Establecimiento;
import com.tecin.mina.model.Usuario;
import com.tecin.mina.model.dto.ResumenImpresionDTO;
import com.tecin.mina.model.dto.ResumenImpresionDTO.*;
import com.tecin.mina.repository.EstablecimientoRepository;
import com.tecin.mina.repository.ExamenRepository;
import com.tecin.mina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResumenImpresionService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private ExamenRepository examenRepository;
    
    @Autowired
    private EstablecimientoRepository establecimientoRepository;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Genera el resumen de un usuario específico con sus exámenes
     */
    public ResumenImpresionDTO generarResumenUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        
        List<Examen> examenes = examenRepository.findByUsuarioId(usuarioId);
        
        ResumenImpresionDTO resumen = new ResumenImpresionDTO(
                "Resumen de Usuario y Aptitudes",
                "USUARIO"
        );
        
        ResumenUsuarioDTO usuarioResumen = new ResumenUsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getEmail()
        );
        
        List<ExamenResumenDTO> examenesResumen = examenes.stream()
                .map(this::convertirExamenAResumen)
                .collect(Collectors.toList());
        
        usuarioResumen.setExamenes(examenesResumen);
        resumen.setUsuarios(Collections.singletonList(usuarioResumen));
        
        return resumen;
    }

    /**
     * Genera el resumen general de todos los usuarios con sus exámenes
     */
    public ResumenImpresionDTO generarResumenTodosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        
        ResumenImpresionDTO resumen = new ResumenImpresionDTO(
                "Resumen General de Usuarios y Aptitudes",
                "USUARIO"
        );
        
        List<ResumenUsuarioDTO> usuariosResumen = usuarios.stream()
                .map(usuario -> {
                    ResumenUsuarioDTO usuarioResumen = new ResumenUsuarioDTO(
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getDocumento(),
                            usuario.getEmail()
                    );
                    
                    List<Examen> examenes = examenRepository.findByUsuarioId(usuario.getId());
                    List<ExamenResumenDTO> examenesResumen = examenes.stream()
                            .map(this::convertirExamenAResumen)
                            .collect(Collectors.toList());
                    
                    usuarioResumen.setExamenes(examenesResumen);
                    return usuarioResumen;
                })
                .collect(Collectors.toList());
        
        resumen.setUsuarios(usuariosResumen);
        return resumen;
    }

    /**
     * Genera el resumen general de establecimientos con sus exámenes requeridos
     */
    public ResumenImpresionDTO generarResumenEstablecimientos() {
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        
        ResumenImpresionDTO resumen = new ResumenImpresionDTO(
                "Resumen General de Establecimientos y Aptitudes Requeridas",
                "ESTABLECIMIENTOS"
        );
        
        List<ResumenEstablecimientoDTO> establecimientosResumen = establecimientos.stream()
                .map(establecimiento -> {
                    ResumenEstablecimientoDTO estResumen = new ResumenEstablecimientoDTO(
                            establecimiento.getId(),
                            establecimiento.getNombre(),
                            establecimiento.getUbicacion(),
                            establecimiento.getDescripcion()
                    );
                    
                    List<ExamenRequeridoResumenDTO> examenesResumen = establecimiento.getExamenesRequeridos().stream()
                            .map(examen -> new ExamenRequeridoResumenDTO(
                                    examen.getTipoExamen().name(),
                                    obtenerLabelTipoExamen(examen.getTipoExamen().name()),
                                    examen.getObservaciones()
                            ))
                            .collect(Collectors.toList());
                    
                    estResumen.setExamenesRequeridos(examenesResumen);
                    return estResumen;
                })
                .collect(Collectors.toList());
        
        resumen.setEstablecimientos(establecimientosResumen);
        return resumen;
    }

    /**
     * Genera el resumen de un establecimiento específico
     */
    public ResumenImpresionDTO generarResumenEstablecimiento(Long establecimientoId) {
        Establecimiento establecimiento = establecimientoRepository.findById(establecimientoId)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con id: " + establecimientoId));
        
        ResumenImpresionDTO resumen = new ResumenImpresionDTO(
                "Resumen de Establecimiento y Aptitudes Requeridas",
                "ESTABLECIMIENTOS"
        );
        
        ResumenEstablecimientoDTO estResumen = new ResumenEstablecimientoDTO(
                establecimiento.getId(),
                establecimiento.getNombre(),
                establecimiento.getUbicacion(),
                establecimiento.getDescripcion()
        );
        
        List<ExamenRequeridoResumenDTO> examenesResumen = establecimiento.getExamenesRequeridos().stream()
                .map(examen -> new ExamenRequeridoResumenDTO(
                        examen.getTipoExamen().name(),
                        obtenerLabelTipoExamen(examen.getTipoExamen().name()),
                        examen.getObservaciones()
                ))
                .collect(Collectors.toList());
        
        estResumen.setExamenesRequeridos(examenesResumen);
        resumen.setEstablecimientos(Collections.singletonList(estResumen));
        
        return resumen;
    }

    // Métodos auxiliares
    
    private ExamenResumenDTO convertirExamenAResumen(Examen examen) {
        String estado = calcularEstado(examen.getFechaCaducidad());
        String tipoExamenStr = examen.getTipoExamen().name();
        
        return new ExamenResumenDTO(
                examen.getId(),
                tipoExamenStr,
                obtenerLabelTipoExamen(tipoExamenStr),
                examen.getFechaEmision().format(DATE_FORMATTER),
                examen.getFechaCaducidad().format(DATE_FORMATTER),
                estado,
                examen.getObservaciones()
        );
    }

    private String calcularEstado(LocalDate fechaCaducidad) {
        LocalDate hoy = LocalDate.now();
        
        if (fechaCaducidad.isBefore(hoy)) {
            return "VENCIDO";
        }
        
        LocalDate proximoAVencer = hoy.plusDays(30);
        if (fechaCaducidad.isBefore(proximoAVencer) || fechaCaducidad.isEqual(proximoAVencer)) {
            return "PROXIMO_A_VENCER";
        }
        
        return "VIGENTE";
    }

    private String obtenerLabelTipoExamen(String tipoExamen) {
        switch(tipoExamen) {
            case "EXAMEN_MEDICO_GENERAL":
                return "Examen Médico General";
            case "EXAMEN_AUDIOMETRICO":
                return "Examen Audiométrico";
            case "EXAMEN_OCUPACIONAL":
                return "Examen Ocupacional";
            case "EXAMEN_PSICOLOGICO":
                return "Examen Psicológico";
            case "EXAMEN_TOXICOLOGICO":
                return "Examen Toxicológico";
            case "EXAMEN_ESPIRACION":
                return "Examen de Espiración";
            case "EXAMEN_RADIOLOGICO":
                return "Examen Radiológico";
            default:
                return tipoExamen;
        }
    }
}
