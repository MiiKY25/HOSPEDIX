package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Empleado;
import org.hospedix.modelos.Huesped;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoHuesped {

    public static Huesped huespedDNI (String dni) {
        ConexionBBDD connection;
        String consulta = "SELECT dni, nombre,apellido,telefono FROM huesped where dni=?";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                int telefono = rs.getInt(4);

                Huesped h=new Huesped(dni,nombre,apellido,telefono);
                return h;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Huesped> todosHuesped() {
        ObservableList<Huesped> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT dni, nombre,apellido,telefono FROM huesped";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                int telefono = rs.getInt(4);

                Huesped h=new Huesped(dni,nombre,apellido,telefono);
                lista.add(h);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean aniadirHuesped(Huesped h) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO huesped (dni, nombre,apellido,telefono) VALUES (?,?,?,?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, h.getDni());
            pstmt.setString(2, h.getNombre());
            pstmt.setString(3, h.getApellido());
            pstmt.setInt(4, h.getTelefono());
            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean actualizarHuesped(Huesped h) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE huesped SET nombre = ?, apellido = ?, telefono = ? WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setString(1, h.getNombre());
            pstmt.setString(2, h.getApellido());
            pstmt.setInt(3, h.getTelefono());
            pstmt.setString(4, h.getDni());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarHuesped(String dni) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "DELETE FROM huesped WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setString(1, dni);
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
