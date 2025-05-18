package org.hospedix.modelos;

import java.util.Objects;

/**
 * Representa a un huésped del hotel con su información personal básica.
 */
public class Huesped {
    private String dni;
    private String nombre;
    private String apellido;
    private int telefono;

    /**
     * Constructor vacío.
     */
    public Huesped() {}

    /**
     * Constructor completo para crear un huésped con todos sus atributos.
     *
     * @param dni      Documento nacional de identidad del huésped.
     * @param nombre   Nombre del huésped.
     * @param apellido Apellido del huésped.
     * @param telefono Teléfono de contacto del huésped.
     */
    public Huesped(String dni, String nombre, String apellido, int telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    /**
     * Obtiene el DNI del huésped.
     *
     * @return DNI del huésped.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del huésped.
     *
     * @param dni Nuevo DNI.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del huésped.
     *
     * @return Nombre del huésped.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del huésped.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del huésped.
     *
     * @return Apellido del huésped.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del huésped.
     *
     * @param apellido Nuevo apellido.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el teléfono del huésped.
     *
     * @return Teléfono del huésped.
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del huésped.
     *
     * @param telefono Nuevo teléfono.
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Compara dos huéspedes por su DNI.
     *
     * @param o Objeto a comparar.
     * @return true si los DNIs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Huesped)) return false;
        Huesped huesped = (Huesped) o;
        return Objects.equals(dni, huesped.dni);
    }

    /**
     * Genera un código hash basado en el DNI del huésped.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    /**
     * Devuelve una representación en texto del huésped.
     *
     * @return Cadena representando al huésped.
     */
    @Override
    public String toString() {
        return dni + " - " + nombre + " " + apellido;
    }

}
