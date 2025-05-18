package org.hospedix.modelos;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa una incidencia reportada en una habitación del hotel.
 */
public class Incidencia {
    private int idIncidencia;
    private String tipoIncidencia;
    private String descripcion;
    private LocalDate fechaReporte;
    private Habitacion habitacion;
    private String estado;

    /**
     * Constructor vacío.
     */
    public Incidencia() {}

    /**
     * Constructor completo para crear una incidencia con todos sus atributos.
     *
     * @param idIncidencia   Identificador único de la incidencia.
     * @param tipoIncidencia Tipo de la incidencia.
     * @param descripcion    Descripción detallada de la incidencia.
     * @param fechaReporte   Fecha en que se reportó la incidencia.
     * @param habitacion     Habitación asociada a la incidencia.
     * @param estado         Estado actual de la incidencia (ej. pendiente, resuelta).
     */
    public Incidencia(int idIncidencia, String tipoIncidencia, String descripcion, LocalDate fechaReporte, Habitacion habitacion, String estado) {
        this.idIncidencia = idIncidencia;
        this.tipoIncidencia = tipoIncidencia;
        this.descripcion = descripcion;
        this.fechaReporte = fechaReporte;
        this.habitacion = habitacion;
        this.estado = estado;
    }

    /**
     * Obtiene el ID de la incidencia.
     *
     * @return ID de la incidencia.
     */
    public int getIdIncidencia() {
        return idIncidencia;
    }

    /**
     * Establece el ID de la incidencia.
     *
     * @param idIncidencia Nuevo ID.
     */
    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    /**
     * Obtiene el tipo de incidencia.
     *
     * @return Tipo de incidencia.
     */
    public String getTipoIncidencia() {
        return tipoIncidencia;
    }

    /**
     * Establece el tipo de incidencia.
     *
     * @param tipoIncidencia Nuevo tipo.
     */
    public void setTipoIncidencia(String tipoIncidencia) {
        this.tipoIncidencia = tipoIncidencia;
    }

    /**
     * Obtiene la descripción de la incidencia.
     *
     * @return Descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la incidencia.
     *
     * @param descripcion Nueva descripción.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene la fecha en la que se reportó la incidencia.
     *
     * @return Fecha de reporte.
     */
    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    /**
     * Establece la fecha de reporte de la incidencia.
     *
     * @param fechaReporte Nueva fecha de reporte.
     */
    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    /**
     * Obtiene la habitación asociada a la incidencia.
     *
     * @return Habitación afectada.
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * Establece la habitación asociada a la incidencia.
     *
     * @param habitacion Nueva habitación.
     */
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    /**
     * Obtiene el estado de la incidencia.
     *
     * @return Estado actual de la incidencia.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la incidencia.
     *
     * @param estado Nuevo estado.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Compara dos incidencias por su ID.
     *
     * @param o Objeto a comparar.
     * @return true si los IDs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Incidencia)) return false;
        Incidencia that = (Incidencia) o;
        return idIncidencia == that.idIncidencia;
    }

    /**
     * Genera un código hash basado en el ID de la incidencia.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idIncidencia);
    }
}
