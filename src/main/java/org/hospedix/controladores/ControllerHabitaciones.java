package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerHabitaciones {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private ComboBox<?> cbEstado;

    @FXML
    private ComboBox<?> cbTipo;

    @FXML
    private TableView<?> tbTablaHabitaciones;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcNumhabitacion;

    @FXML
    private TableColumn<?, ?> tcPrecio;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField tfNumhabitacion;

    @FXML
    private TextField tfPrecio;

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
