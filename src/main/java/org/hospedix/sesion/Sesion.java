package org.hospedix.sesion;

import org.hospedix.modelos.Empleado;

public class Sesion {
    private static Empleado empleadoActual;

    public static Empleado getEmpleadoActual() {
        return empleadoActual;
    }

    public static void setEmpleadoActual(Empleado empleado) {
        Sesion.empleadoActual = empleado;
    }
}
