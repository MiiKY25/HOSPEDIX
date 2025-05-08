package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMenu {

    @FXML
    private Label txtEmpleado;

    private void cambiarVentana(String rutaFXML, ActionEvent event, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + rutaFXML));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí puedes agregar una alerta o log más detallado si deseas
        }
    }

    @FXML
    void accionEmpleados(ActionEvent event) {
        cambiarVentana("empleados.fxml", event, "Gestión de Empleados");
    }

    @FXML
    void accionHabitaciones(ActionEvent event) {
        cambiarVentana("habitaciones.fxml", event, "Gestión de Habitaciones");
    }

    @FXML
    void accionHuespedes(ActionEvent event) {
        cambiarVentana("huesped.fxml", event, "Gestión de Huéspedes");
    }

    @FXML
    void accionIncidencias(ActionEvent event) {
        cambiarVentana("incidencias.fxml", event, "Gestión de Incidencias");
    }

    @FXML
    void accionReservas(ActionEvent event) {
        cambiarVentana("reservas.fxml", event, "Gestión de Reservas");
    }

    @FXML
    void accionVolver(ActionEvent event) {
        cambiarVentana("inicio.fxml", event, "Inicio de Sesión");
    }
}
