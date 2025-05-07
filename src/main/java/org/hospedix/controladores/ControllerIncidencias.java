package org.hospedix.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerIncidencias {

    @FXML
    private ComboBox<?> cbEstado;

    @FXML
    private ComboBox<?> cbTipo;

    @FXML
    private TableView<?> tbTablaIncidencias;

    @FXML
    private TableColumn<?, ?> tcDescripcion;

    @FXML
    private TableColumn<?, ?> tcEstado;

    @FXML
    private TableColumn<?, ?> tcFecha;

    @FXML
    private TableColumn<?, ?> tcHabitacion;

    @FXML
    private TableColumn<?, ?> tcID;

    @FXML
    private TableColumn<?, ?> tcTipo;

    @FXML
    private TextField tfDescripcion;

    @FXML
    private TextField tfFecha;

    @FXML
    private TextField tfHabitacion;

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
