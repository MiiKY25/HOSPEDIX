package org.hospedix.controladores;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.hospedix.bbdd.ConexionBBDD;
import org.hospedix.dao.DaoEmpleado;
import org.hospedix.modelos.Empleado;
import org.hospedix.sesion.Sesion;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerInicio {

    @FXML
    private TextField txtDNI;

    @FXML
    void accionIniciarSesion(ActionEvent event) throws IOException {
        String dni = txtDNI.getText();
        // Verifica que los campos de entrada no estén vacíos
        if (dni.isEmpty()) {
            mostrarError("Introduce un DNI");
        } else {
            Empleado empleado=DaoEmpleado.empleadoDNI(dni);
            // Verifica las credenciales del empleado
            if (empleado!=null) {
                //Guardar el empleado en la sesion
                Sesion.setEmpleadoActual(empleado); // GUARDAR EL EMPLEADO EN LA SESIÓN

                // Cargar el nuevo FXML si las credenciales son correctas
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Menú Principal");
                stage.show();
            } else {
                // Muestra un error si las credenciales son incorrectas
                mostrarError("Usuario o contraseña Incorrectos");
                txtDNI.setText("");
            }
        }
    }

    /**
     * Muestra un mensaje de error en una alerta.
     *
     * @param error el mensaje de error a mostrar.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        // Controlar acceso a la base de datos
        try {
            new ConexionBBDD();
        } catch (SQLException e) {
            mostrarError("Conexion Erronea a la Base de Datos");
            Platform.exit(); // Cierra la aplicación
            return;
        }
    }
}