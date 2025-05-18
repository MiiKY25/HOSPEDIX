package org.hospedix.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.modelos.Habitacion;
import org.hospedix.modelos.Incidencia;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DaoIncidencia {

    /**
     * Obtiene todas las incidencias registradas en la base de datos.
     *
     * @return ObservableList con todas las incidencias.
     */
    public static ObservableList<Incidencia> todasIncidencias() {
        ObservableList<Incidencia> lista = FXCollections.observableArrayList();
        ConexionBBDD connection;
        String consulta = "SELECT id_incidencia, tipo_incidencia, descripcion, fecha_reporte, num_habitacion, estado FROM incidencias";
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

                Habitacion habitacion = DaoHabitacion.buscarHabitacion(num_habitacion);

                Incidencia i = new Incidencia(id_incidencia, tipo_incidencia, descripcion, fecha_reporte, habitacion, estado);
                lista.add(i);
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Añade una nueva incidencia en la base de datos.
     * La fecha de reporte se asigna automáticamente como la fecha actual.
     *
     * @param i Objeto Incidencia con los datos a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public static boolean aniadirIncidencia(Incidencia i) {
        ConexionBBDD connection;
        int resul = 0;
        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO incidencias (tipo_incidencia, descripcion, fecha_reporte, num_habitacion, estado) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setString(1, i.getTipoIncidencia());
            pstmt.setString(2, i.getDescripcion());
            pstmt.setDate(3, Date.valueOf(LocalDate.now()));  // Fecha actual
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

    /**
     * Actualiza los datos de una incidencia existente.
     *
     * @param i Objeto Incidencia con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public static boolean actualizarIncidencia(Incidencia i) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "UPDATE incidencias SET tipo_incidencia = ?, descripcion = ?, num_habitacion = ?, estado = ? WHERE id_incidencia = ?";
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

    /**
     * Elimina una incidencia según su ID.
     *
     * @param id ID de la incidencia a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public static boolean eliminarIncidencia(int id) {
        ConexionBBDD connection;
        try {
            connection = new ConexionBBDD();
            String sql = "DELETE FROM incidencias WHERE id_incidencia = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
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
