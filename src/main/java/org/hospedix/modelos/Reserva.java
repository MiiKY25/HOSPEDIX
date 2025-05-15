package org.hospedix.modelos;

import java.sql.Date;
import java.util.Objects;

public class Reserva {
    private int idReserva;
    private int numHabitacion;
    private String dniHuesped;
    private Date fechaCheckin;
    private Date fechaCheckout;
    private String estadoPago;
    private int cantidadPersonas;
    private int precio;
    private int extras;

    public Reserva() {
    }

    public Reserva(int idReserva, int numHabitacion, String dniHuesped, Date fechaCheckin, Date fechaCheckout,
                   String estadoPago, int cantidadPersonas, int precio, int extras) {
        this.idReserva = idReserva;
        this.numHabitacion = numHabitacion;
        this.dniHuesped = dniHuesped;
        this.fechaCheckin = fechaCheckin;
        this.fechaCheckout = fechaCheckout;
        this.estadoPago = estadoPago;
        this.cantidadPersonas = cantidadPersonas;
        this.precio = precio;
        this.extras = extras;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getNumHabitacion() {
        return numHabitacion;
    }

    public void setNumHabitacion(int numHabitacion) {
        this.numHabitacion = numHabitacion;
    }

    public String getDniHuesped() {
        return dniHuesped;
    }

    public void setDniHuesped(String dniHuesped) {
        this.dniHuesped = dniHuesped;
    }

    public Date getFechaCheckin() {
        return fechaCheckin;
    }

    public void setFechaCheckin(Date fechaCheckin) {
        this.fechaCheckin = fechaCheckin;
    }

    public Date getFechaCheckout() {
        return fechaCheckout;
    }

    public void setFechaCheckout(Date fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getExtras() {
        return extras;
    }

    public void setExtras(int extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return idReserva == reserva.idReserva;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", numHabitacion=" + numHabitacion +
                ", dniHuesped='" + dniHuesped + '\'' +
                ", fechaCheckin=" + fechaCheckin +
                ", fechaCheckout=" + fechaCheckout +
                ", estadoPago='" + estadoPago + '\'' +
                ", cantidadPersonas=" + cantidadPersonas +
                ", precio=" + precio +
                ", extras=" + extras +
                '}';
    }
}
