package com.tecin.mina.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ResumenImpresionDTO {
    private String titulo;
    private LocalDateTime fechaGeneracion;
    private String tipo; // "USUARIO" o "ESTABLECIMIENTOS"
    private List<ResumenUsuarioDTO> usuarios;
    private List<ResumenEstablecimientoDTO> establecimientos;

    public ResumenImpresionDTO() {
    }

    public ResumenImpresionDTO(String titulo, String tipo) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.fechaGeneracion = LocalDateTime.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<ResumenUsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<ResumenUsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ResumenEstablecimientoDTO> getEstablecimientos() {
        return establecimientos;
    }

    public void setEstablecimientos(List<ResumenEstablecimientoDTO> establecimientos) {
        this.establecimientos = establecimientos;
    }

    public static class ResumenUsuarioDTO {
        private Long id;
        private String nombre;
        private String documento;
        private String email;
        private List<ExamenResumenDTO> examenes;

        public ResumenUsuarioDTO() {
        }

        public ResumenUsuarioDTO(Long id, String nombre, String documento, String email) {
            this.id = id;
            this.nombre = nombre;
            this.documento = documento;
            this.email = email;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public List<ExamenResumenDTO> getExamenes() {
            return examenes;
        }

        public void setExamenes(List<ExamenResumenDTO> examenes) {
            this.examenes = examenes;
        }
    }

    public static class ExamenResumenDTO {
        private Long id;
        private String tipoExamen;
        private String tipoExamenLabel;
        private String fechaEmision;
        private String fechaCaducidad;
        private String estado;
        private String observaciones;

        public ExamenResumenDTO() {
        }

        public ExamenResumenDTO(Long id, String tipoExamen, String tipoExamenLabel, String fechaEmision, String fechaCaducidad, String estado, String observaciones) {
            this.id = id;
            this.tipoExamen = tipoExamen;
            this.tipoExamenLabel = tipoExamenLabel;
            this.fechaEmision = fechaEmision;
            this.fechaCaducidad = fechaCaducidad;
            this.estado = estado;
            this.observaciones = observaciones;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTipoExamen() {
            return tipoExamen;
        }

        public void setTipoExamen(String tipoExamen) {
            this.tipoExamen = tipoExamen;
        }

        public String getTipoExamenLabel() {
            return tipoExamenLabel;
        }

        public void setTipoExamenLabel(String tipoExamenLabel) {
            this.tipoExamenLabel = tipoExamenLabel;
        }

        public String getFechaEmision() {
            return fechaEmision;
        }

        public void setFechaEmision(String fechaEmision) {
            this.fechaEmision = fechaEmision;
        }

        public String getFechaCaducidad() {
            return fechaCaducidad;
        }

        public void setFechaCaducidad(String fechaCaducidad) {
            this.fechaCaducidad = fechaCaducidad;
        }

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }

    public static class ResumenEstablecimientoDTO {
        private Long id;
        private String nombre;
        private String ubicacion;
        private String descripcion;
        private List<ExamenRequeridoResumenDTO> examenesRequeridos;

        public ResumenEstablecimientoDTO() {
        }

        public ResumenEstablecimientoDTO(Long id, String nombre, String ubicacion, String descripcion) {
            this.id = id;
            this.nombre = nombre;
            this.ubicacion = ubicacion;
            this.descripcion = descripcion;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getUbicacion() {
            return ubicacion;
        }

        public void setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public List<ExamenRequeridoResumenDTO> getExamenesRequeridos() {
            return examenesRequeridos;
        }

        public void setExamenesRequeridos(List<ExamenRequeridoResumenDTO> examenesRequeridos) {
            this.examenesRequeridos = examenesRequeridos;
        }
    }

    public static class ExamenRequeridoResumenDTO {
        private String tipoExamen;
        private String tipoExamenLabel;
        private String observaciones;

        public ExamenRequeridoResumenDTO() {
        }

        public ExamenRequeridoResumenDTO(String tipoExamen, String tipoExamenLabel, String observaciones) {
            this.tipoExamen = tipoExamen;
            this.tipoExamenLabel = tipoExamenLabel;
            this.observaciones = observaciones;
        }

        public String getTipoExamen() {
            return tipoExamen;
        }

        public void setTipoExamen(String tipoExamen) {
            this.tipoExamen = tipoExamen;
        }

        public String getTipoExamenLabel() {
            return tipoExamenLabel;
        }

        public void setTipoExamenLabel(String tipoExamenLabel) {
            this.tipoExamenLabel = tipoExamenLabel;
        }

        public String getObservaciones() {
            return observaciones;
        }

        public void setObservaciones(String observaciones) {
            this.observaciones = observaciones;
        }
    }
}
