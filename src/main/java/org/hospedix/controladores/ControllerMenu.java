package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.hospedix.modelos.Empleado;
import org.hospedix.sesion.Sesion;

import java.io.IOException;

/**
 * Controlador para el menú principal de la aplicación.
 * Permite la navegación entre diferentes pantallas de gestión,
 * muestra el nombre del empleado actualmente logueado,
 * y gestiona la acción de cerrar sesión.
 */
public class ControllerMenu {

    /**
     * Etiqueta donde se muestra el nombre del empleado actual en mayúsculas.
     */
    @FXML
    private Label txtEmpleado;

    /**
     * Metodo auxiliar para cambiar la ventana actual por otra.
     *
     * @param rutaFXML La ruta del archivo FXML a cargar (sin la carpeta /fxml/).
     * @param event    El evento generado que activa el cambio.
     * @param titulo   El título que se mostrará en la ventana.
     */
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
        }
    }

    /**
     * Acción para navegar a la gestión de empleados.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionEmpleados(ActionEvent event) {
        cambiarVentana("empleados.fxml", event, "Gestión de Empleados");
    }

    /**
     * Acción para navegar a la gestión de habitaciones.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionHabitaciones(ActionEvent event) {
        cambiarVentana("habitaciones.fxml", event, "Gestión de Habitaciones");
    }

    /**
     * Acción para navegar a la gestión de huéspedes.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionHuespedes(ActionEvent event) {
        cambiarVentana("huesped.fxml", event, "Gestión de Huéspedes");
    }

    /**
     * Acción para navegar a la gestión de incidencias.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionIncidencias(ActionEvent event) {
        cambiarVentana("incidencias.fxml", event, "Gestión de Incidencias");
    }

    /**
     * Acción para navegar a la gestión de reservas.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionReservas(ActionEvent event) {
        cambiarVentana("reservas.fxml", event, "Gestión de Reservas");
    }

    /**
     * Acción para cerrar sesión y volver a la pantalla de inicio.
     *
     * @param event Evento disparado por la acción del usuario.
     */
    @FXML
    void accionVolver(ActionEvent event) {
        Sesion.setEmpleadoActual(null);
        cambiarVentana("inicio.fxml", event, "Inicio de Sesión");
    }

    /**
     * Metodo que se ejecuta automáticamente al cargar el controlador.
     * Inicializa la etiqueta con el nombre en mayúsculas del empleado actual.
     */
    @FXML
    private void initialize() {
        Empleado empleado = Sesion.getEmpleadoActual();
        if (empleado != null) {
            txtEmpleado.setText(empleado.getNombre().toUpperCase());
        }
    }
}
