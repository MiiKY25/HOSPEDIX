package org.hospedix.dao;

import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Empleado;

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
}
