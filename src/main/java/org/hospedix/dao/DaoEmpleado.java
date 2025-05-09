package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Empleado;
import org.hospedix.modelos.Habitacion;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoEmpleado {

    public static Empleado empleadoDNI (String dni) {
        ConexionBBDD connection;
        String consulta = "SELECT dni, nombre,apellido,telefono,direccion,cargo,horario_trabajo FROM empleados where dni=?";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, dni);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                int telefono = rs.getInt(4);
                String direccion = rs.getString(5);
                String cargo= rs.getString(6);
                String horario_trabajo= rs.getString(6);
                Empleado e=new Empleado(dni,nombre,apellido,telefono,direccion,cargo,horario_trabajo);
                return e;
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Empleado> todosEmpleados() {
        ObservableList<Empleado> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT dni, nombre,apellido,telefono,direccion,cargo,horario_trabajo FROM empleados";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String dni = rs.getString(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                int telefono = rs.getInt(4);
                String direccion = rs.getString(5);
                String cargo= rs.getString(6);
                String horario_trabajo= rs.getString(7);
                Empleado e=new Empleado(dni,nombre,apellido,telefono,direccion,cargo,horario_trabajo);
                lista.add(e);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean aniadirEmpleado(Empleado e) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO empleados (dni, nombre,apellido,telefono,direccion,cargo,horario_trabajo) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, e.getDni());
            pstmt.setString(2, e.getNombre());
            pstmt.setString(3, e.getApellido());
            pstmt.setInt(4, e.getTelefono());
            pstmt.setString(5, e.getDireccion());
            pstmt.setString(6, e.getCargo());
            pstmt.setString(7, e.getHorario_trabajo());
            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean actualizarEmpleado(Empleado e) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE empleados SET nombre = ?, apellido = ?, telefono = ?, direccion = ?, cargo = ?, horario_trabajo = ? WHERE dni = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setString(1, e.getNombre());
            pstmt.setString(2, e.getApellido());
            pstmt.setInt(3, e.getTelefono());
            pstmt.setString(4, e.getDireccion());
            pstmt.setString(5, e.getCargo());
            pstmt.setString(6, e.getHorario_trabajo());
            pstmt.setString(7, e.getDni());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean eliminarEmpleado(String dni) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "DELETE FROM empleados WHERE dni = ?";
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
