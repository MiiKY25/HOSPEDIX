package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Habitacion;
import org.hospedix.modelos.Huesped;
import org.hospedix.modelos.Incidencia;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DaoIncidencia {

    public static ObservableList<Incidencia> todasIncidencias() {
        ObservableList<Incidencia> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT id_incidencia, tipo_incidencia,descripcion,fecha_reporte,num_habitacion,estado FROM incidencias";
        try {
            connection = new ConexionBBDD();
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id_incidencia = rs.getInt(1);
                String tipo_incidencia = rs.getString(2);
                String descripcion = rs.getString(3);
                LocalDate fecha_reporte = rs.getDate(4).toLocalDate();
                int num_habitacion = rs.getInt(5);
                String estado = rs.getString(6);

                Habitacion habitacion=DaoHabitacion.buscarHabitacion(num_habitacion);

                Incidencia i=new Incidencia(id_incidencia,tipo_incidencia,descripcion,fecha_reporte,habitacion,estado);
                lista.add(i);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static boolean aniadirIncidencia(Incidencia i) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO incidencias (tipo_incidencia,descripcion,fecha_reporte,num_habitacion,estado) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, i.getTipoIncidencia());
            pstmt.setString(2, i.getDescripcion());
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));
            pstmt.setInt(4, i.getHabitacion().getNumHabitacion());
            pstmt.setString(5, i.getEstado());
            resul = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resul > 0;
    }

    public static boolean actualizarIncidencia(Incidencia i) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE incidencias SET tipo_incidencia = ?, descripcion = ?, num_habitacion = ? , estado = ? WHERE id_incidencia = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setString(1, i.getTipoIncidencia());
            pstmt.setString(2, i.getDescripcion());
            pstmt.setInt(3, i.getHabitacion().getNumHabitacion());
            pstmt.setString(4, i.getEstado());
            pstmt.setInt(5, i.getIdIncidencia());
            int filas = pstmt.executeUpdate();
            pstmt.close();
            connection.CloseConexion();
            return filas > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }



}
