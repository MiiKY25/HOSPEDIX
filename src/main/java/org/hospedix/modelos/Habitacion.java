package org.hospedix.modelos;

import java.util.Objects;

public class Habitacion {
    private int numHabitacion;
    private String estado;
    private String tipo;
    private double precio;

    public Habitacion() {}

    public Habitacion(int numHabitacion, String estado, String tipo, double precio) {
        this.numHabitacion = numHabitacion;
        this.estado = estado;
        this.tipo = tipo;
        this.precio = precio;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habitacion)) return false;
        Habitacion that = (Habitacion) o;
        return numHabitacion == that.numHabitacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numHabitacion);
    }

    @Override
    public String toString() {
        return "Habitación N°" + numHabitacion + " - " + tipo;
    }

}
