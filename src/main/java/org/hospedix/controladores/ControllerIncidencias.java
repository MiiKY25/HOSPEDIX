package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerIncidencias {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> colDescripcion;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colFecha;

    @FXML
    private TableColumn<?, ?> colHabitacion;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colTipo;

    @FXML
    private ComboBox<?> comboEstado;

    @FXML
    private ComboBox<?> comboHabitacion;

    @FXML
    private ComboBox<?> comboTipo;

    @FXML
    private DatePicker fecha;

    @FXML
    private TableView<?> tablaIncidencias;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtID;

    @FXML
    void accionAniadir(ActionEvent event) {

    }

    @FXML
    void accionEditar(ActionEvent event) {

    }

    @FXML
    void accionEliminar(ActionEvent event) {

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
