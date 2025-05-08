package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.hospedix.dao.DaoEmpleado;
import org.hospedix.modelos.Empleado;

import java.io.IOException;

public class ControllerInicio {

    @FXML
    private TextField txtDNI;

    @FXML
    void accionIniciarSesion(ActionEvent event) {
        String dni = txtDNI.getText().trim();
        DaoEmpleado dao = new DaoEmpleado();
        Empleado empleado = dao.empleadoDNI(dni);

        if (empleado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
                Scene scene = new Scene(loader.load());

                // Si necesitas pasar el empleado al siguiente controlador:
                // ControllerMenu controller = loader.getController();
                // controller.setEmpleado(empleado);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Menú Principal");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de inicio de sesión");
            alert.setHeaderText(null);
            alert.setContentText("DNI no encontrado. Verifique e intente nuevamente.");
            alert.showAndWait();
        }
    }
}