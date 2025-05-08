package org.hospedix.modelos;

import java.util.Date;
import java.util.Objects;

public class Incidencia {
    private int idIncidencia;
    private String tipoIncidencia;
    private String descripcion;
    private Date fechaReporte;
    private int numHabitacion;
    private String estado;

    public Incidencia() {}

    public Incidencia(int idIncidencia, String tipoIncidencia, String descripcion, Date fechaReporte, int numHabitacion, String estado) {
        this.idIncidencia = idIncidencia;
        this.tipoIncidencia = tipoIncidencia;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.numHabitacion = numHabitacion;
        this.estado = estado;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(Date fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incidencia)) return false;
        Incidencia that = (Incidencia) o;
        return idIncidencia == that.idIncidencia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIncidencia);
    }
}
