package org.hospedix.modelos;

import java.util.Objects;

/**
 * Representa una habitación del hotel con su número, estado, tipo y precio.
 */
public class Habitacion {
    private int numHabitacion;
    private String estado;
    private String tipo;
    private double precio;

    /**
     * Constructor vacío.
     */
    public Habitacion() {}

    /**
     * Constructor completo para crear una habitación con todos sus atributos.
     *
     * @param numHabitacion Número de habitación.
     * @param estado         Estado actual de la habitación (Disponible, Ocupada, Mantenimiento).
     * @param tipo           Tipo de habitación (Individual, Doble, Suite).
     * @param precio         Precio por noche de la habitación.
     */
    public Habitacion(int numHabitacion, String estado, String tipo, double precio) {
        this.numHabitacion = numHabitacion;
        this.estado = estado;
        this.tipo = tipo;
        this.precio = precio;
    }

    /**
     * Obtiene el número de la habitación.
     *
     * @return Número de habitación.
     */
    public int getNumHabitacion() {
        return numHabitacion;
    }

    /**
     * Establece el número de la habitación.
     *
     * @param numHabitacion Nuevo número de habitación.
     */
    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    /**
     * Obtiene el estado actual de la habitación.
     *
     * @return Estado de la habitación.
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado de la habitación.
     *
     * @param estado Nuevo estado (Disponible, Ocupada, Mantenimiento).
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el tipo de habitación.
     *
     * @return Tipo de habitación.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de habitación.
     *
     * @param tipo Nuevo tipo (Individual, Doble, Suite).
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el precio por noche de la habitación.
     *
     * @return Precio de la habitación.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio por noche de la habitación.
     *
     * @param precio Nuevo precio.
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Compara dos habitaciones por su número.
     *
     * @param o Objeto a comparar.
     * @return true si los números de habitación son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion)) return false;
        Habitacion that = (Habitacion) o;
        return numHabitacion == that.numHabitacion;
    }

    /**
     * Genera un código hash basado en el número de habitación.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numHabitacion);
    }

    /**
     * Devuelve una representación en texto de la habitación.
     *
     * @return Cadena representando la habitación.
     */
    @Override
    public String toString() {
        return "Habitación N°" + numHabitacion + " - " + tipo;
    }
}
