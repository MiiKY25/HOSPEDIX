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

}
