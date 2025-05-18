package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hospedix.dao.DaoHabitacion;
import org.hospedix.dao.DaoIncidencia;
import org.hospedix.modelos.Habitacion;
import org.hospedix.modelos.Incidencia;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Controlador para gestionar la interfaz y lógica de incidencias en el sistema Hospedix.
 * Permite añadir, editar, eliminar, limpiar formularios y volver al menú principal.
 * Gestiona una tabla con incidencias y campos para su manipulación.
 */
public class ControllerIncidencias {

    /** Botón para añadir una nueva incidencia */
    @FXML
    private Button btnAniadir;

    /** Botón para editar una incidencia seleccionada */
    @FXML
    private Button btnEditar;

    /** Botón para eliminar una incidencia seleccionada */
    @FXML
    private Button btnEliminar;

    /** Columna de la tabla que muestra la descripción de la incidencia */
    @FXML
    private TableColumn<Incidencia, String> colDescripcion;

    /** Columna de la tabla que muestra el estado de la incidencia */
    @FXML
    private TableColumn<Incidencia, String> colEstado;

    /** Columna de la tabla que muestra la fecha de reporte de la incidencia */
    @FXML
    private TableColumn<Incidencia, LocalDate> colFecha;

    /** Columna de la tabla que muestra la habitación asociada a la incidencia */
    @FXML
    private TableColumn<Incidencia, Habitacion> colHabitacion;

    /** Columna de la tabla que muestra el ID de la incidencia */
    @FXML
    private TableColumn<Incidencia, Integer> colID;

    /** Columna de la tabla que muestra el tipo de incidencia */
    @FXML
    private TableColumn<Incidencia, String> colTipo;

    /** ComboBox para seleccionar el estado de la incidencia */
    @FXML
    private ComboBox<String> comboEstado;

    /** ComboBox para seleccionar la habitación asociada a la incidencia */
    @FXML
    private ComboBox<Habitacion> comboHabitacion;

    /** ComboBox para seleccionar el tipo de incidencia */
    @FXML
    private ComboBox<String> comboTipo;

    /** Selector de fecha para indicar la fecha de reporte de la incidencia */
    @FXML
    private DatePicker fecha;

    /** Tabla que muestra la lista de incidencias */
    @FXML
    private TableView<Incidencia> tablaIncidencias;

    /** Área de texto para ingresar la descripción de la incidencia */
    @FXML
    private TextArea txtDescripcion;

    /** Campo de texto que muestra el ID de la incidencia (no editable) */
    @FXML
    private TextField txtID;

    /** Incidencia actualmente seleccionada en la tabla */
    private Incidencia incidenciaSeleccionado = null;

    /**
     * Acción para añadir una nueva incidencia.
     * Valida los datos, crea una nueva incidencia y actualiza la tabla.
     *
     * @param event evento de acción del botón añadir.
     */
    @FXML
    void accionAniadir(ActionEvent event) {
        String error = validarDatos();
        if (error.isEmpty()) {
            // Crear Incidencia con ID 0 temporalmente
            Incidencia i = new Incidencia(0, comboTipo.getValue(), txtDescripcion.getText(), null, comboHabitacion.getValue(), comboEstado.getValue());
            Boolean estado = DaoIncidencia.aniadirIncidencia(i);
            if (estado) {
                DaoHabitacion.habitacionMantenimiento(i.getHabitacion().getNumHabitacion());
                mostrarInfo("Incidencia creada correctamente");
                limpiarCampos();
                cargarIncidencias();
            } else {
                mostrarError("Error al crear la Incidencia");
            }
        } else {
            mostrarError(error);
        }
    }

    /**
     * Acción para editar una incidencia seleccionada.
     * Valida los datos, actualiza la incidencia en la base de datos y refresca la tabla.
     *
     * @param event evento de acción del botón editar.
     */
    @FXML
    void accionEditar(ActionEvent event) {
        Incidencia i = tablaIncidencias.getSelectionModel().getSelectedItem();
        if (i != null) {
            String error = validarDatos();
            if (error.isEmpty()) {
                Incidencia IncidenciaNueva = new Incidencia(Integer.parseInt(txtID.getText()), comboTipo.getValue(), txtDescripcion.getText(), fecha.getValue(), comboHabitacion.getValue(), comboEstado.getValue());
                Boolean estado = DaoIncidencia.actualizarIncidencia(IncidenciaNueva);
                if (estado) {
                    mostrarInfo("Incidencia editada correctamente");
                    limpiarCampos();
                    cargarIncidencias();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al editar la Incidencia");
                }
            } else {
                mostrarError(error);
            }
        } else {
            mostrarError("Selecciona una Incidencia");
        }
    }

    /**
     * Acción para eliminar una incidencia seleccionada.
     * Solicita confirmación antes de eliminar y actualiza la tabla.
     *
     * @param event evento de acción del botón eliminar.
     */
    @FXML
    void accionEliminar(ActionEvent event) {
        Incidencia i = tablaIncidencias.getSelectionModel().getSelectedItem();
        if (i != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("¿Estás seguro de que deseas eliminar esta incidencia?");
            alerta.setContentText("Esta acción no se puede deshacer.");

            ButtonType botonSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType botonNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alerta.getButtonTypes().setAll(botonSi, botonNo);

            alerta.showAndWait().ifPresent(respuesta -> {
                if (respuesta == botonSi) {
                    Boolean estado = DaoIncidencia.eliminarIncidencia(i.getIdIncidencia());
                    if (estado) {
                        mostrarInfo("Incidencia eliminada correctamente.");
                        limpiarCampos();
                        cargarIncidencias();
                        estadoInicialBotones();
                    } else {
                        mostrarError("Error al eliminar la incidencia.");
                    }
                }
            });
        } else {
            mostrarError("Selecciona una incidencia.");
        }
    }

    /**
     * Acción para limpiar los campos del formulario.
     *
     * @param event evento de acción del botón limpiar.
     */
    @FXML
    void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Acción para volver al menú principal.
     *
     * @param event evento de acción del botón volver.
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
     * Configura el evento click en la tabla para cargar la incidencia seleccionada
     * en los campos del formulario y ajustar los botones y controles correspondientes.
     */
    private void configurarEventosTabla() {
        tablaIncidencias.setOnMouseClicked(event -> {
            incidenciaSeleccionado = tablaIncidencias.getSelectionModel().getSelectedItem();

            if (incidenciaSeleccionado != null) {
                txtID.setText(String.valueOf(incidenciaSeleccionado.getIdIncidencia()));
                txtDescripcion.setText(String.valueOf(incidenciaSeleccionado.getDescripcion()));

                comboEstado.getSelectionModel().clearSelection();
                comboEstado.getSelectionModel().select(incidenciaSeleccionado.getEstado());

                comboTipo.getSelectionModel().clearSelection();
                comboTipo.getSelectionModel().select(incidenciaSeleccionado.getTipoIncidencia());

                comboHabitacion.getSelectionModel().clearSelection();
                comboHabitacion.getSelectionModel().select(incidenciaSeleccionado.getHabitacion());

                fecha.setValue(incidenciaSeleccionado.getFechaReporte());

                // Deshabilitar selección de habitación durante edición
                comboHabitacion.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    /**
     * Valida los datos ingresados en el formulario para una incidencia.
     *
     * @return un String con los mensajes de error si existen, o cadena vacía si todoo es válido.
     */
    public String validarDatos() {
        String error = "";

        String descripcion = txtDescripcion.getText().trim();
        if (descripcion.isEmpty()) {
            error += "El campo 'Descripción' no puede estar vacío.\n";
        } else if (descripcion.length() > 100) {
            error += "El campo 'Descripción' no puede tener más de 100 caracteres.\n";
        }

        Habitacion habitacion = comboHabitacion.getValue();
        if (habitacion == null) {
            error += "Debes seleccionar una Habitacion.\n";
        }

        String tipo = comboTipo.getValue();
        if (tipo == null || tipo.trim().isEmpty()) {
            error += "Debes seleccionar un Tipo.\n";
        }

        String estado = comboEstado.getValue();
        if (estado == null || estado.trim().isEmpty()) {
            error += "Debes seleccionar un Estado.\n";
        }

        return error;
    }

    /**
     * Establece el estado inicial de los botones: deshabilita editar y eliminar, habilita añadir.
     */
    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }

    /**
     * Carga todas las incidencias desde la base de datos y las muestra en la tabla.
     */
    void cargarIncidencias() {
        ObservableList<Incidencia> listaIncidencias = DaoIncidencia.todasIncidencias();
        tablaIncidencias.setItems(listaIncidencias);
        tablaIncidencias.refresh();
    }

    /**
     * Limpia todos los campos del formulario y restablece el estado de los botones y controles.
     */
    private void limpiarCampos() {
        txtID.setText("");
        txtDescripcion.setText("");
        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();
        comboHabitacion.getSelectionModel().selectFirst();
        fecha.setValue(null);
        btnAniadir.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        comboHabitacion.setDisable(false);
    }

    /**
     * Muestra un mensaje de error en una ventana de alerta.
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
     * Muestra un mensaje informativo en una ventana de alerta.
     *
     * @param info mensaje de información a mostrar.
     */
    void mostrarInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información");
        alert.setContentText(info);
        alert.showAndWait();
    }

    /**
     * Metodo llamado automáticamente después de cargar el FXML.
     * Configura las columnas de la tabla, carga los datos iniciales y establece eventos.
     */
    @FXML
    private void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idIncidencia"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("habitacion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Configurar la columna de fecha con formato personalizado
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReporte"));
        colFecha.setCellFactory(column -> new TableCell<Incidencia, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(formatter));
                }
            }
        });

        comboEstado.setItems(FXCollections.observableArrayList("Abierto", "Cerrado"));
        comboTipo.setItems(FXCollections.observableArrayList("Limpieza", "Mantenimiento", "Otros"));

        ObservableList<Habitacion> listaHabitaciones = DaoHabitacion.todasHabitaciones();
        comboHabitacion.getItems().addAll(listaHabitaciones);

        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();
        comboHabitacion.getSelectionModel().selectFirst();

        configurarEventosTabla();
        cargarIncidencias();
        estadoInicialBotones();
    }
}
