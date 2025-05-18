package org.hospedix.modelos;

import java.util.Objects;

/**
 * Representa un empleado del hotel con sus datos personales y laborales.
 */
public class Empleado {
    private String dni;
    private String nombre;
    private String apellido;
    private int telefono;
    private String direccion;
    private String cargo;
    private String horario_trabajo;

    /**
     * Constructor vacío.
     */
    public Empleado() {}

    /**
     * Constructor completo para crear un empleado con todos sus atributos.
     *
     * @param dni            Documento Nacional de Identidad del empleado.
     * @param nombre         Nombre del empleado.
     * @param apellido       Apellido del empleado.
     * @param telefono       Número de teléfono del empleado.
     * @param direccion      Dirección del empleado.
     * @param cargo          Cargo o puesto que desempeña en el hotel.
     * @param horarioTrabajo Horario laboral asignado al empleado.
     */
    public Empleado(String dni, String nombre, String apellido, int telefono, String direccion, String cargo, String horarioTrabajo) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cargo = cargo;
        this.horario_trabajo = horarioTrabajo;
    }

    /**
     * Obtiene el DNI del empleado.
     *
     * @return DNI del empleado.
     */
    public String getDni() {
        return dni;
    }

    /**
     * Establece el DNI del empleado.
     *
     * @param dni Nuevo DNI.
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     * Obtiene el nombre del empleado.
     *
     * @return Nombre del empleado.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del empleado.
     *
     * @param nombre Nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido del empleado.
     *
     * @return Apellido del empleado.
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Establece el apellido del empleado.
     *
     * @param apellido Nuevo apellido.
     */
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    /**
     * Obtiene el teléfono del empleado.
     *
     * @return Número de teléfono.
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del empleado.
     *
     * @param telefono Nuevo número de teléfono.
     */
    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene la dirección del empleado.
     *
     * @return Dirección del empleado.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección del empleado.
     *
     * @param direccion Nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el cargo del empleado.
     *
     * @return Cargo del empleado.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Establece el cargo del empleado.
     *
     * @param cargo Nuevo cargo.
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Obtiene el horario de trabajo del empleado.
     *
     * @return Horario laboral.
     */
    public String getHorario_trabajo() {
        return horario_trabajo;
    }

    /**
     * Establece el horario de trabajo del empleado.
     *
     * @param horario_trabajo Nuevo horario laboral.
     */
    public void setHorario_trabajo(String horario_trabajo) {
        this.horario_trabajo = horario_trabajo;
    }

    /**
     * Compara dos empleados por su DNI.
     *
     * @param o Objeto a comparar.
     * @return true si los DNIs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empleado)) return false;
        Empleado empleado = (Empleado) o;
        return Objects.equals(dni, empleado.dni);
    }

    /**
     * Genera un código hash basado en el DNI.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }
}
