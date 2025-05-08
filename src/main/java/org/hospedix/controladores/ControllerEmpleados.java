package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerEmpleados {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> colApellidos;

    @FXML
    private TableColumn<?, ?> colCargo;

    @FXML
    private TableColumn<?, ?> colDNI;

    @FXML
    private TableColumn<?, ?> colDireccion;

    @FXML
    private TableColumn<?, ?> colHorario;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colTekefono;

    @FXML
    private ComboBox<?> comboCargo;

    @FXML
    private TableView<?> tablaEmpleados;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtHorario;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    void accionAniadir(ActionEvent event) {
        // Implementación pendiente
    }

    @FXML
    void accionEditar(ActionEvent event) {
        // Implementación pendiente
    }

    @FXML
    void accionEliminar(ActionEvent event) {
        // Implementación pendiente
    }

    @FXML
    void accionVolver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Menú Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Aquí podrías lanzar una alerta si lo deseas
        }
    }
}
