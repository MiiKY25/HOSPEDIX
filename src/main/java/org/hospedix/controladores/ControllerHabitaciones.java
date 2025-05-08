package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerHabitaciones {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colHabitacion;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private TableColumn<?, ?> colTipo;

    @FXML
    private ComboBox<?> comboEstado;

    @FXML
    private ComboBox<?> comboTipo;

    @FXML
    private TableView<?> tablaHabitacion;

    @FXML
    private TextField txtHabitacion;

    @FXML
    private TextField txtPrecio;

    @FXML
    void accionAniadir(ActionEvent event) {
        // Lógica pendiente
    }

    @FXML
    void accionEditar(ActionEvent event) {
        // Lógica pendiente
    }

    @FXML
    void accionEliminar(ActionEvent event) {
        // Lógica pendiente
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
        }
    }
}
