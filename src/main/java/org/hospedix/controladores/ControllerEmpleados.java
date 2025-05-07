package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerEmpleados {

    @FXML
    private ComboBox<?> cbCargo;

    @FXML
    private TableView<?> tbTablaEmpleados;

    @FXML
    private TableColumn<?, ?> tcApellidos;

    @FXML
    private TableColumn<?, ?> tcCargo;

    @FXML
    private TableColumn<?, ?> tcDireccion;

    @FXML
    private TableColumn<?, ?> tcDni;

    @FXML
    private TableColumn<?, ?> tcHorario;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfDireccion;

    @FXML
    private TextField tfDni;

    @FXML
    private TextField tfHorario;

    @FXML
    private TextField tfNombre;

    @FXML
    private TableColumn<?, ?> tcTelefono;

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
