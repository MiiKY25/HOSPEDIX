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

/**
 * Controlador para la ventana de inicio de sesión.
 * Gestiona la autenticación del empleado mediante su DNI,
 * el manejo de errores y la navegación hacia el menú principal.
 */
public class ControllerInicio {

    @FXML
    private TextField txtDNI;

    /**
     * Acción que se ejecuta al pulsar el botón para iniciar sesión.
     * Verifica que el DNI no esté vacío, busca el empleado en la base de datos,
     * guarda el empleado en la sesión y cambia a la escena del menú principal.
     * Si no encuentra el empleado, muestra un mensaje de error.
     *
     * @param event el evento generado por la acción del usuario.
     * @throws IOException si ocurre un error al cargar la vista FXML.
     */
    @FXML
    void accionIniciarSesion(ActionEvent event) throws IOException {
        String dni = txtDNI.getText();
        // Verifica que los campos de entrada no estén vacíos
        if (dni.isEmpty()) {
            mostrarError("Introduce un DNI");
        } else {
            Empleado empleado = DaoEmpleado.empleadoDNI(dni);
            // Verifica las credenciales del empleado
            if (empleado != null) {
                // Guardar el empleado en la sesión
                Sesion.setEmpleadoActual(empleado);

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
     * Muestra un mensaje de error en una ventana de alerta.
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

    /**
     * Metodo llamado automáticamente después de cargar el FXML.
     * Intenta establecer la conexión con la base de datos,
     * si falla muestra un error y cierra la aplicación.
     */
    @FXML
    void initialize() {
        try {
            new ConexionBBDD();
        } catch (SQLException e) {
            mostrarError("Conexion Erronea a la Base de Datos");
            Platform.exit(); // Cierra la aplicación
        }
    }
}