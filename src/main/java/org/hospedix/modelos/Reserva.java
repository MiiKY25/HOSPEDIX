package org.hospedix.modelos;

import java.sql.Date;
import java.util.Objects;

/**
 * Representa una reserva realizada por un huésped en una habitación del hotel.
 */
public class Reserva {
    private int idReserva;
    private Habitacion habitacion;
    private Huesped huesped;
    private Date fechaCheckin;
    private Date fechaCheckout;
    private String estadoPago;
    private int cantidadPersonas;
    private int precio;
    private int extras;

    /**
     * Constructor vacío.
     */
    public Reserva() {
    }

    /**
     * Constructor completo para crear una reserva con todos sus atributos.
     *
     * @param idReserva         Identificador único de la reserva.
     * @param habitacion        Habitación reservada.
     * @param huesped           Huésped que realiza la reserva.
     * @param fechaCheckin      Fecha de entrada (check-in).
     * @param fechaCheckout     Fecha de salida (check-out).
     * @param estadoPago        Estado del pago (ej. pagado, pendiente).
     * @param cantidadPersonas  Número de personas en la reserva.
     * @param precio            Precio total de la reserva.
     * @param extras            Coste adicional por servicios extra.
     */
    public Reserva(int idReserva, Habitacion habitacion, Huesped huesped, Date fechaCheckin, Date fechaCheckout,
                   String estadoPago, int cantidadPersonas, int precio, int extras) {
        this.idReserva = idReserva;
        this.habitacion = habitacion;
        this.huesped = huesped;
        this.fechaCheckin = fechaCheckin;
        this.fechaCheckout = fechaCheckout;
        this.estadoPago = estadoPago;
        this.cantidadPersonas = cantidadPersonas;
        this.precio = precio;
        this.extras = extras;
    }

    /**
     * Obtiene el ID de la reserva.
     *
     * @return ID de la reserva.
     */
    public int getIdReserva() {
        return idReserva;
    }

    /**
     * Establece el ID de la reserva.
     *
     * @param idReserva Nuevo ID.
     */
    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    /**
     * Obtiene la fecha de check-in.
     *
     * @return Fecha de entrada.
     */
    public Date getFechaCheckin() {
        return fechaCheckin;
    }

    /**
     * Establece la fecha de check-in.
     *
     * @param fechaCheckin Nueva fecha de entrada.
     */
    public void setFechaCheckin(Date fechaCheckin) {
        this.fechaCheckin = fechaCheckin;
    }

    /**
     * Obtiene la fecha de check-out.
     *
     * @return Fecha de salida.
     */
    public Date getFechaCheckout() {
        return fechaCheckout;
    }

    /**
     * Establece la fecha de check-out.
     *
     * @param fechaCheckout Nueva fecha de salida.
     */
    public void setFechaCheckout(Date fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }

    /**
     * Obtiene la habitación asociada a la reserva.
     *
     * @return Habitación reservada.
     */
    public Habitacion getHabitacion() {
        return habitacion;
    }

    /**
     * Establece la habitación asociada a la reserva.
     *
     * @param habitacion Nueva habitación.
     */
    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    /**
     * Obtiene el huésped que hizo la reserva.
     *
     * @return Huésped.
     */
    public Huesped getHuesped() {
        return huesped;
    }

    /**
     * Establece el huésped de la reserva.
     *
     * @param huesped Nuevo huésped.
     */
    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    /**
     * Obtiene el estado del pago de la reserva.
     *
     * @return Estado del pago.
     */
    public String getEstadoPago() {
        return estadoPago;
    }

    /**
     * Establece el estado del pago.
     *
     * @param estadoPago Nuevo estado de pago.
     */
    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    /**
     * Obtiene la cantidad de personas incluidas en la reserva.
     *
     * @return Número de personas.
     */
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    /**
     * Establece la cantidad de personas en la reserva.
     *
     * @param cantidadPersonas Nuevo número de personas.
     */
    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    /**
     * Obtiene el precio total de la reserva.
     *
     * @return Precio total.
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio total de la reserva.
     *
     * @param precio Nuevo precio.
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el valor de los extras añadidos a la reserva.
     *
     * @return Coste adicional.
     */
    public int getExtras() {
        return extras;
    }

    /**
     * Establece los extras de la reserva.
     *
     * @param extras Nuevo coste de extras.
     */
    public void setExtras(int extras) {
        this.extras = extras;
    }

    /**
     * Compara dos reservas por su ID.
     *
     * @param o Objeto a comparar.
     * @return true si los IDs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reserva)) return false;
        Reserva reserva = (Reserva) o;
        return idReserva == reserva.idReserva;
    }

    /**
     * Genera un código hash basado en el ID de la reserva.
     *
     * @return Código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(idReserva);
    }
}
