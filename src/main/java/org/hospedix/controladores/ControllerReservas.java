package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerReservas {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colEstado;

    @FXML
    private TableColumn<?, ?> colExtras;

    @FXML
    private TableColumn<?, ?> colHabitacion;

    @FXML
    private TableColumn<?, ?> colID;

    @FXML
    private TableColumn<?, ?> colIN;

    @FXML
    private TableColumn<?, ?> colOUT;

    @FXML
    private TableColumn<?, ?> colPersonas;

    @FXML
    private TableColumn<?, ?> colPrecio;

    @FXML
    private ComboBox<?> comboCliente;

    @FXML
    private ComboBox<?> comboEstado;

    @FXML
    private ComboBox<?> comboHabitacion;

    @FXML
    private DatePicker fechaIN;

    @FXML
    private DatePicker fechaOUT;

    @FXML
    private TableView<?> tablaReservas;

    @FXML
    private TextField txtCantPersonas;

    @FXML
    private TextField txtExtras;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtPrecio;

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
