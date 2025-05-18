package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * Controlador para la gestión de empleados.
 * Permite añadir, editar, eliminar y listar empleados,
 * así como validar los datos ingresados en la interfaz.
 */
public class ControllerEmpleados {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAniadir;

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

    /**
     * Referencia al empleado actualmente seleccionado en la tabla.
     */
    private Empleado empleadoSeleccionado = null;

    /**
     * Acción para añadir un nuevo empleado a la base de datos.
     * Valida los datos antes de insertar y muestra mensajes de error o éxito.
     *
     * @param event Evento de acción generado al pulsar el botón.
     */
    @FXML
    void accionAniadir(ActionEvent event) {
        String error = validarDatos();
        if (error.isEmpty()) {
            Empleado empleado = DaoEmpleado.empleadoDNI(txtDNI.getText());
            if (empleado == null) {
                Empleado e = new Empleado(
                        txtDNI.getText(),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        Integer.parseInt(txtTelefono.getText()),
                        txtDireccion.getText(),
                        comboCargo.getValue(),
                        txtHorario.getText()
                );
                Boolean estado = DaoEmpleado.aniadirEmpleado(e);
                if (estado) {
                    mostrarInfo("Empleado creado correctamente");
                    limpiarCampos();
                    cargarEmpleados();
                } else {
                    mostrarError("Error al crear el Empleado");
                }
            } else {
                mostrarError("Ya existe un empleado con el DNI: " + txtDNI.getText());
            }
        } else {
            mostrarError(error);
        }
    }

    /**
     * Acción para editar el empleado seleccionado.
     * Valida los datos antes de actualizar y muestra mensajes de error o éxito.
     *
     * @param event Evento de acción generado al pulsar el botón.
     */
    @FXML
    void accionEditar(ActionEvent event) {
        Empleado e = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (e != null) {
            String error = validarDatos();
            if (error.isEmpty()) {
                Empleado empleadoNuevo = new Empleado(
                        e.getDni(),
                        txtNombre.getText(),
                        txtApellidos.getText(),
                        Integer.parseInt(txtTelefono.getText()),
                        txtDireccion.getText(),
                        comboCargo.getValue(),
                        txtHorario.getText()
                );
                Boolean estado = DaoEmpleado.actualizarEmpleado(empleadoNuevo);
                if (estado) {
                    mostrarInfo("Empleado editado correctamente");
                    limpiarCampos();
                    cargarEmpleados();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al editar el Empleado");
                }
            } else {
                mostrarError(error);
            }
        } else {
            mostrarError("Selecciona un Empleado");
        }
    }

    /**
     * Acción para eliminar el empleado seleccionado.
     * Solicita confirmación antes de proceder y muestra mensajes según resultado.
     *
     * @param event Evento de acción generado al pulsar el botón.
     */
    @FXML
    void accionEliminar(ActionEvent event) {
        Empleado e = tablaEmpleados.getSelectionModel().getSelectedItem();
        if (e != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar este empleado?");
            alerta.setContentText("Esta acción no se puede deshacer.");

            ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alerta.getButtonTypes().setAll(botonSi, botonNo);

            alerta.showAndWait().ifPresent(respuesta -> {
                if (respuesta == botonSi) {
                    Boolean estado = DaoEmpleado.eliminarEmpleado(e.getDni());
                    if (estado) {
                        mostrarInfo("Empleado eliminado correctamente.");
                        limpiarCampos();
                        cargarEmpleados();
                        estadoInicialBotones();
                    } else {
                        mostrarError("Error al eliminar el empleado.");
                    }
                }
            });
        } else {
            mostrarError("Selecciona un empleado.");
        }
    }

    /**
     * Limpia los campos del formulario.
     *
     * @param event Evento de acción generado al pulsar el botón.
     */
    @FXML
    void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Vuelve al menú principal de la aplicación.
     *
     * @param event Evento de acción generado al pulsar el botón.
     */
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

    /**
     * Valida los datos ingresados en el formulario.
     *
     * @return Un string con mensajes de error; vacío si no hay errores.
     */
    public String validarDatos() {
        String error = "";

        String dni = txtDNI.getText().trim();
        if (dni.isEmpty()) {
            error += "El campo 'DNI' no puede estar vacío.\n";
        } else if (!dni.matches("\\d{8}[A-Za-z]")) {
            error += "El campo 'DNI' debe tener 8 números seguidos de una letra (ej: 12345678A).\n";
        }

        if (txtNombre.getText().trim().isEmpty()) {
            error += "El campo 'Nombre' no puede estar vacío.\n";
        }

        if (txtApellidos.getText().trim().isEmpty()) {
            error += "El campo 'Apellidos' no puede estar vacío.\n";
        }

        if (txtDireccion.getText().trim().isEmpty()) {
            error += "El campo 'Dirección' no puede estar vacío.\n";
        }

        if (txtHorario.getText().trim().isEmpty()) {
            error += "El campo 'Horario' no puede estar vacío.\n";
        }

        String telefono = txtTelefono.getText().trim();
        if (telefono.isEmpty()) {
            error += "El campo 'Teléfono' no puede estar vacío.\n";
        } else if (!telefono.matches("\\d{9}")) {
            error += "El teléfono debe tener exactamente 9 dígitos numéricos.\n";
        }

        String cargo = comboCargo.getValue();
        if (cargo == null || cargo.trim().isEmpty()) {
            error += "Debes seleccionar un cargo.\n";
        }

        return error;
    }

    /**
     * Carga todos los empleados desde la base de datos y los muestra en la tabla.
     */
    void cargarEmpleados() {
        ObservableList<Empleado> listaEmpleados = DaoEmpleado.todosEmpleados();
        tablaEmpleados.setItems(listaEmpleados);
        tablaEmpleados.refresh();
    }

    /**
     * Configura los eventos de selección en la tabla para llenar los campos
     * con los datos del empleado seleccionado.
     */
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

                comboCargo.getSelectionModel().clearSelection();
                comboCargo.getSelectionModel().select(empleadoSeleccionado.getCargo());

                txtDNI.setDisable(true);

                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    /**
     * Establece el estado inicial de los botones (editar y eliminar deshabilitados,
     * añadir habilitado).
     */
    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }

    /**
     * Limpia todos los campos del formulario y restablece el estado inicial.
     */
    private void limpiarCampos() {
        txtDNI.setText("");
        txtNombre.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtHorario.setText("");
        txtDireccion.setText("");
        comboCargo.getSelectionModel().selectFirst();
        empleadoSeleccionado = null;
        txtDNI.setDisable(false);
        btnAniadir.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
    }

    /**
     * Muestra una alerta de error con el mensaje proporcionado.
     *
     * @param error Mensaje de error a mostrar.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta informativa con el mensaje proporcionado.
     *
     * @param info Mensaje de información a mostrar.
     */
    void mostrarInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * Metodo inicializador de JavaFX. Configura la tabla, los comboBox,
     * eventos y carga los empleados.
     */
    @FXML
    private void initialize() {
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario_trabajo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));

        comboCargo.setItems(FXCollections.observableArrayList(
                "Recepcionista", "Gerente", "Limpieza", "Mantenimiento", "Hostelería", "Otros"
        ));
        comboCargo.getSelectionModel().selectFirst();

        configurarEventosTabla();
        cargarEmpleados();
        estadoInicialBotones();
    }
}
