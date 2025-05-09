package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hospedix.dao.DaoEmpleado;
import org.hospedix.modelos.Empleado;

import java.io.IOException;

public class ControllerEmpleados {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Empleado, String> colApellidos;

    @FXML
    private TableColumn<Empleado, String> colCargo;

    @FXML
    private TableColumn<Empleado, String> colDNI;

    @FXML
    private TableColumn<Empleado, String> colDireccion;

    @FXML
    private TableColumn<Empleado, String> colHorario;

    @FXML
    private TableColumn<Empleado, String> colNombre;

    @FXML
    private TableColumn<Empleado, Integer> colTelefono;

    @FXML
    private ComboBox<String> comboCargo;

    @FXML
    private TableView<Empleado> tablaEmpleados;

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
        }
    }

    void cargarEmpleados() {
        ObservableList<Empleado> listaEmpleados = DaoEmpleado.todosEmpleados();
        tablaEmpleados.setItems(listaEmpleados);
    }

    @FXML
    private void initialize() {
        // Configuración de las columnas de la tabla
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario_trabajo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

       //Cargar comobobox
        comboCargo.setItems(FXCollections.observableArrayList(
                "Recepcionista", "Gerente", "Limpieza", "Mantenimiento", "Hostelería", "Otros"
        ));
        comboCargo.getSelectionModel().selectFirst();

        cargarEmpleados();
    }
}
