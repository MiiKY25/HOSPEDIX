package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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

    }

}
