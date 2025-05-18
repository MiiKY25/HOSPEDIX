package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Reserva;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoReserva {

    /**
     * Obtiene todas las reservas almacenadas en la base de datos.
     *
     * @return ObservableList con todas las reservas.
     */
    public static ObservableList<Reserva> todasReservas() {
        ObservableList<Reserva> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT id_reserva, num_habitacion, dni_huesped, fecha_checkin, fecha_checkout, estado_pago, cantidad_personas, precio, extras FROM reservas";

        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getInt("id_reserva"),
                        DaoHabitacion.buscarHabitacion(rs.getInt("num_habitacion")),
                        DaoHuesped.huespedDNI(rs.getString("dni_huesped")),
                        rs.getDate("fecha_checkin"),
                        rs.getDate("fecha_checkout"),
                        rs.getString("estado_pago"),
                        rs.getInt("cantidad_personas"),
                        rs.getInt("precio"),
                        rs.getInt("extras")
                );
                lista.add(r);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Añade una nueva reserva a la base de datos.
     *
     * @param r Objeto Reserva con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean aniadirReserva(Reserva r) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO reservas (num_habitacion, dni_huesped, fecha_checkin, fecha_checkout, estado_pago, cantidad_personas, precio, extras) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, r.getHabitacion().getNumHabitacion());
            pstmt.setString(2, r.getHuesped().getDni());
            pstmt.setDate(3, new Date(r.getFechaCheckin().getTime()));
            pstmt.setDate(4, new Date(r.getFechaCheckout().getTime()));
            pstmt.setString(5, r.getEstadoPago());
            pstmt.setInt(6, r.getCantidadPersonas());
            pstmt.setInt(7, r.getPrecio());
            pstmt.setInt(8, r.getExtras());

            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Actualiza los datos de una reserva existente.
     *
     * @param r Objeto Reserva con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizarReserva(Reserva r) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE reservas SET num_habitacion = ?, dni_huesped = ?, fecha_checkin = ?, fecha_checkout = ?, estado_pago = ?, cantidad_personas = ?, precio = ?, extras = ? WHERE id_reserva = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setInt(1, r.getHabitacion().getNumHabitacion());
            pstmt.setString(2, r.getHuesped().getDni());
            pstmt.setDate(3, new Date(r.getFechaCheckin().getTime()));
            pstmt.setDate(4, new Date(r.getFechaCheckout().getTime()));
            pstmt.setString(5, r.getEstadoPago());
            pstmt.setInt(6, r.getCantidadPersonas());
            pstmt.setInt(7, r.getPrecio());
            pstmt.setInt(8, r.getExtras());
            pstmt.setInt(9, r.getIdReserva());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Elimina una reserva por su ID.
     *
     * @param idReserva ID de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarReserva(int idReserva) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "DELETE FROM reservas WHERE id_reserva = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setInt(1, idReserva);
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Verifica si existe una reserva con un ID específico.
     * Esta implementación consulta directamente a la base para optimizar rendimiento.
     *
     * @param id ID de la reserva.
     * @return true si existe la reserva, false en caso contrario.
     */
    public static boolean existeReservaConId(int id) {
        ConexionBBDD connection;
        boolean existe = false;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT 1 FROM reservas WHERE id_reserva = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            existe = rs.next();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return existe;
    }
}
