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
import org.hospedix.modelos.Habitacion;

import java.io.IOException;

public class ControllerEmpleados {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAniadir;

    @FXML
    private Button btnLimpiar;

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

    private Empleado empleadoSeleccionado = null;

    @FXML
    void accionAniadir(ActionEvent event) {
        String error=validarDatos();
        if (error.isEmpty()){
            Empleado empleado=DaoEmpleado.empleadoDNI(txtDNI.getText());
            if (empleado==null){
                //Crear Empleado
                Empleado e=new Empleado(txtDNI.getText(),txtNombre.getText(),txtApellidos.getText(),Integer.parseInt(txtTelefono.getText()),txtDireccion.getText(),comboCargo.getValue(),txtHorario.getText());
                Boolean estado = DaoEmpleado.aniadirEmpleado(e);
                if (estado) {
                    mostrarInfo("Emplado creado correctamente");
                    limpiarCampos();
                    cargarEmpleados();
                } else {
                    mostrarError("Error al crear el Empleado");
                }
            }else {
                mostrarError("Ya existe un empleado con el DNI: "+txtDNI.getText());
            }
        }else {
            mostrarError(error);
        }
    }

    @FXML
    void accionEditar(ActionEvent event) {
        Empleado e=tablaEmpleados.getSelectionModel().getSelectedItem();
        if (e!=null){
            String error=validarDatos();
            if (error.isEmpty()){
                Empleado EmpleadoNuevo=new Empleado(e.getDni(),txtNombre.getText(),txtApellidos.getText(),Integer.parseInt(txtTelefono.getText()),txtDireccion.getText(),comboCargo.getValue(),txtHorario.getText());
                Boolean estado = DaoEmpleado.actualizarEmpleado(EmpleadoNuevo);
                if (estado) {
                    mostrarInfo("Empleado editado correctamente");
                    limpiarCampos();
                    cargarEmpleados();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al editar el Empleado");
                }
            }else{
                mostrarError(error);
            }
        }else {
            mostrarError("Selecciona un Empleado");
        }
    }

    @FXML
    void accionEliminar(ActionEvent event) {
        Empleado e=tablaEmpleados.getSelectionModel().getSelectedItem();
        if (e!=null){
                Boolean estado = DaoEmpleado.eliminarEmpleado(e.getDni());
                if (estado) {
                    mostrarInfo("Empleado eliminado correctamente");
                    limpiarCampos();
                    cargarEmpleados();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al eliminar el Empleado");
                }
        }else {
            mostrarError("Selecciona un Empleado");
        }
    }

    @FXML
    void accionLimpiar(ActionEvent event) {
        limpiarCampos();
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

    public String validarDatos() {
        String error = "";

        // Validar DNI
        String dni = txtDNI.getText().trim();
        if (dni.isEmpty()) {
            error += "El campo 'DNI' no puede estar vacío.\n";
        } else if (!dni.matches("\\d{8}[A-Za-z]")) {
            error += "El campo 'DNI' debe tener 8 números seguidos de una letra (ej: 12345678A).\n";
        }

        // Validar nombre
        if (txtNombre.getText().trim().isEmpty()) {
            error += "El campo 'Nombre' no puede estar vacío.\n";
        }

        // Validar apellidos
        if (txtApellidos.getText().trim().isEmpty()) {
            error += "El campo 'Apellidos' no puede estar vacío.\n";
        }

        // Validar dirección
        if (txtDireccion.getText().trim().isEmpty()) {
            error += "El campo 'Dirección' no puede estar vacío.\n";
        }

        // Validar horario
        if (txtHorario.getText().trim().isEmpty()) {
            error += "El campo 'Horario' no puede estar vacío.\n";
        }

        // Validar teléfono
        String telefono = txtTelefono.getText().trim();
        if (telefono.isEmpty()) {
            error += "El campo 'Teléfono' no puede estar vacío.\n";
        } else if (!telefono.matches("\\d{9}")) {
            error += "El teléfono debe tener exactamente 9 dígitos numéricos.\n";
        }

        // Validar cargo
        String cargo = comboCargo.getValue();
        if (cargo == null || cargo.trim().isEmpty()) {
            error += "Debes seleccionar un cargo.\n";
        }

        return error;
    }



    void cargarEmpleados() {
        ObservableList<Empleado> listaEmpleados = DaoEmpleado.todosEmpleados();
        tablaEmpleados.setItems(listaEmpleados);
        tablaEmpleados.refresh();
    }

    private void configurarEventosTabla() {
        tablaEmpleados.setOnMouseClicked(event -> {
            empleadoSeleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();

            if (empleadoSeleccionado != null) {
                txtDNI.setText(String.valueOf(empleadoSeleccionado.getDni()));
                txtDireccion.setText(String.valueOf(empleadoSeleccionado.getDireccion()));
                txtHorario.setText(String.valueOf(empleadoSeleccionado.getHorario_trabajo()));
                txtTelefono.setText(String.valueOf(empleadoSeleccionado.getTelefono()));
                txtApellidos.setText(String.valueOf(empleadoSeleccionado.getApellido()));
                txtNombre.setText(String.valueOf(empleadoSeleccionado.getNombre()));

                // Forzar refresco de ComboBox
                comboCargo.getSelectionModel().clearSelection();
                comboCargo.getSelectionModel().select(empleadoSeleccionado.getCargo());

                txtDNI.setDisable(true);

                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }

    private void limpiarCampos() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtHorario.setText("");
        txtDireccion.setText("");
        comboCargo.getSelectionModel().selectFirst();
        empleadoSeleccionado=null;
        txtDNI.setDisable(false);
        btnAniadir.setDisable(false);
    }

    /**
     * Muestra un mensaje de error en una alerta.
     *
     * @param error el mensaje de error a mostrar.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje informativo en una alerta.
     *
     * @param info mensaje de información a mostrar.
     */
    void mostrarInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText(info);
        alert.showAndWait();
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

        configurarEventosTabla();
        cargarEmpleados();
        estadoInicialBotones();
    }
}
