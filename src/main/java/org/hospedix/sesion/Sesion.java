package org.hospedix.sesion;

import org.hospedix.modelos.Empleado;

/**
 * Clase que gestiona la sesión actual del empleado en la aplicación.
 * Permite almacenar y recuperar el empleado que está logueado en el sistema.
 * La información se mantiene estática para acceso global durante la ejecución.
 */
public class Sesion {

    /**
     * Empleado actualmente logueado en la sesión.
     */
    private static Empleado empleadoActual;

    /**
     * Obtiene el empleado que está actualmente logueado en la sesión.
     *
     * @return el objeto {@link Empleado} correspondiente al usuario activo,
     *         o {@code null} si no hay ningún empleado logueado.
     */
    public static Empleado getEmpleadoActual() {
        return empleadoActual;
    }

    /**
     * Establece el empleado que queda registrado como logueado en la sesión.
     *
     * @param empleado el objeto {@link Empleado} que representa al usuario que
     *                 inicia sesión.
     */
    public static void setEmpleadoActual(Empleado empleado) {
        Sesion.empleadoActual = empleado;
    }
}
