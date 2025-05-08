package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerInicio {

    @FXML
    private TextField txtDNI;

    @FXML
    void accionIniciarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el Stage actual desde el botón u otro nodo
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Puedes agregar aquí una alerta para mostrar el error
        }
    }
}
