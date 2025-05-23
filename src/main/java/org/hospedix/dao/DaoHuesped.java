package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Huesped;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoHuesped {

    /**
     * Obtiene un huésped a partir de su DNI.
     *
     * @param dni DNI del huésped a buscar.
     * @return Objeto Huesped si se encuentra, null si no existe o en caso de error.
     */
    public static Huesped huespedDNI(String dni) {
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

                Huesped h = new Huesped(dni, nombre, apellido, telefono);
                return h;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene una lista con todos los huéspedes de la base de datos.
     *
     * @return ObservableList con todos los huéspedes.
     */
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

                Huesped h = new Huesped(dni, nombre, apellido, telefono);
                lista.add(h);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Añade un nuevo huésped a la base de datos.
     *
     * @param h Objeto Huesped con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Actualiza los datos de un huésped existente.
     *
     * @param h Objeto Huesped con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
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

    /**
     * Elimina un huésped según su DNI.
     *
     * @param dni DNI del huésped a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
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

    /**
     * Comprueba si un huésped tiene reservas asociadas.
     *
     * @param dni DNI del huésped a comprobar.
     * @return true si tiene reservas, false si no tiene o en caso de error.
     */
    public static boolean huespedTieneReservas(String dni) {
        ConexionBBDD connection;
        boolean tiene = false;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT COUNT(*) FROM reservas WHERE dni_huesped = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, dni);
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

    /**
     * Elimina un huésped y todas sus reservas asociadas en una transacción.
     *
     * @param dni DNI del huésped a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarHuespedYReservas(String dni) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            connection.getConnection().setAutoCommit(false); // Iniciar transacción

            // Eliminar reservas primero
            String deleteReservas = "DELETE FROM reservas WHERE dni_huesped = ?";
            PreparedStatement pstmt1 = connection.getConnection().prepareStatement(deleteReservas);
            pstmt1.setString(1, dni);
            pstmt1.executeUpdate();
            pstmt1.close();

            // Luego eliminar al huésped
            String deleteHuesped = "DELETE FROM huesped WHERE dni = ?";
            PreparedStatement pstmt2 = connection.getConnection().prepareStatement(deleteHuesped);
            pstmt2.setString(1, dni);
            int filas = pstmt2.executeUpdate();
            pstmt2.close();

            connection.getConnection().commit(); // Confirmar transacción
            connection.CloseConexion();

            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
