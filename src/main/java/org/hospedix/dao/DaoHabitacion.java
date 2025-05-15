package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Habitacion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoHabitacion {

    public static ObservableList<Habitacion> todasHabitaciones() {
        ObservableList<Habitacion> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT num_habitacion, estado, tipo, precio FROM habitaciones";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num_habitacion");
                String estado = rs.getString("estado");
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio");

                Habitacion h = new Habitacion(num, estado, tipo, precio);
                lista.add(h);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static ObservableList<Habitacion> todasHabitacionesDisponibles() {
        ObservableList<Habitacion> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT num_habitacion, estado, tipo, precio FROM habitaciones WHERE estado = 'Disponible'";

        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int num = rs.getInt("num_habitacion");
                String estado = rs.getString("estado");
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio");

                Habitacion h = new Habitacion(num, estado, tipo, precio);
                lista.add(h);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean aniadirHabitacion(Habitacion h) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO habitaciones (num_habitacion, estado, tipo, precio) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, h.getNumHabitacion());
            pstmt.setString(2, h.getEstado());
            pstmt.setString(3, h.getTipo());
            pstmt.setDouble(4, h.getPrecio());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Habitacion buscarHabitacion(int num) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT * FROM habitaciones WHERE num_habitacion = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, num);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Habitacion(
                        rs.getInt("num_habitacion"),
                        rs.getString("estado"),
                        rs.getString("tipo"),
                        rs.getDouble("precio")
                );
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean actualizarHabitacion(Habitacion h) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE habitaciones SET estado = ?, tipo = ?, precio = ? WHERE num_habitacion = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setString(1, h.getEstado());
            pstmt.setString(2, h.getTipo());
            pstmt.setDouble(3, h.getPrecio());
            pstmt.setInt(4, h.getNumHabitacion());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarHabitacion(int numero) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "DELETE FROM habitaciones WHERE num_habitacion = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setInt(1, numero);
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean habitacionTieneReservas(int numHabitacion) {
        ConexionBBDD connection;
        boolean tiene = false;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT COUNT(*) FROM reservas WHERE num_habitacion = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, numHabitacion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tiene = rs.getInt(1) > 0;
            }
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiene;
    }

    public static boolean habitacionTieneIncidencias(int numHabitacion) {
        ConexionBBDD connection;
        boolean tiene = false;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT COUNT(*) FROM incidencias WHERE num_habitacion = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, numHabitacion);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tiene = rs.getInt(1) > 0;
            }
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiene;
    }

    public static boolean eliminarHabitacionYAsociados(int numHabitacion) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            connection.getConnection().setAutoCommit(false); // Iniciar transacción

            // Eliminar incidencias primero
            String deleteIncidencias = "DELETE FROM incidencias WHERE num_habitacion = ?";
            PreparedStatement pstmt1 = connection.getConnection().prepareStatement(deleteIncidencias);
            pstmt1.setInt(1, numHabitacion);
            pstmt1.executeUpdate();
            pstmt1.close();

            // Luego eliminar reservas
            String deleteReservas = "DELETE FROM reservas WHERE num_habitacion = ?";
            PreparedStatement pstmt2 = connection.getConnection().prepareStatement(deleteReservas);
            pstmt2.setInt(1, numHabitacion);
            pstmt2.executeUpdate();
            pstmt2.close();

            // Finalmente eliminar la habitación
            String deleteHabitacion = "DELETE FROM habitaciones WHERE num_habitacion = ?";
            PreparedStatement pstmt3 = connection.getConnection().prepareStatement(deleteHabitacion);
            pstmt3.setInt(1, numHabitacion);
            int filas = pstmt3.executeUpdate();
            pstmt3.close();

            connection.getConnection().commit(); // Confirmar transacción
            connection.CloseConexion();

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
