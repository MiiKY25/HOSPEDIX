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

public class ControllerIncidencias {

    @FXML
    private Button btnAniadir;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private TableColumn<Incidencia, String> colDescripcion;

    @FXML
    private TableColumn<Incidencia, String> colEstado;

    @FXML
    private TableColumn<Incidencia, LocalDate> colFecha;

    @FXML
    private TableColumn<Incidencia, Habitacion> colHabitacion;

    @FXML
    private TableColumn<Incidencia, Integer> colID;

    @FXML
    private TableColumn<Incidencia, String> colTipo;

    @FXML
    private ComboBox<String> comboEstado;

    @FXML
    private ComboBox<Habitacion> comboHabitacion;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private DatePicker fecha;

    @FXML
    private TableView<Incidencia> tablaIncidencias;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtID;

    private Incidencia incidenciaSeleccionado = null;

    @FXML
    void accionAniadir(ActionEvent event) {
        String error=validarDatos();
        if (error.isEmpty()){
            //Crear Incidencia
            Incidencia i=new Incidencia(0,comboTipo.getValue(),txtDescripcion.getText(),null,comboHabitacion.getValue(),comboEstado.getValue());
            Boolean estado = DaoIncidencia.aniadirIncidencia(i);
            if (estado) {
                DaoHabitacion.habitacionMantenimiento(i.getHabitacion().getNumHabitacion());
                mostrarInfo("Incidencia creada correctamente");
                limpiarCampos();
                cargarIncidencias();
            } else {
                mostrarError("Error al crear la Incidencia");
            }
        }else {
            mostrarError(error);
        }
    }

    @FXML
    void accionEditar(ActionEvent event) {
        Incidencia i=tablaIncidencias.getSelectionModel().getSelectedItem();
        if (i!=null){
            String error=validarDatos();
            if (error.isEmpty()){
                Incidencia IncidenciaNueva=new Incidencia(Integer.parseInt(txtID.getText()),comboTipo.getValue(),txtDescripcion.getText(),fecha.getValue(),comboHabitacion.getValue(),comboEstado.getValue());
                Boolean estado = DaoIncidencia.actualizarIncidencia(IncidenciaNueva);
                if (estado) {
                    mostrarInfo("Incidencia editada correctamente");
                    limpiarCampos();
                    cargarIncidencias();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al editar la Incidencia");
                }
            }else{
                mostrarError(error);
            }
        }else {
            mostrarError("Selecciona una Incidencia");
        }
    }

    @FXML
    void accionEliminar(ActionEvent event) {
        Incidencia i=tablaIncidencias.getSelectionModel().getSelectedItem();
        if (i!=null){
            Boolean estado = DaoIncidencia.eliminarIncidencia(i.getIdIncidencia());
            if (estado) {
                mostrarInfo("Incidencia eliminada correctamente");
                limpiarCampos();
                cargarIncidencias();
                estadoInicialBotones();
            } else {
                mostrarError("Error al eliminar la Incidencia");
            }
        }else {
            mostrarError("Selecciona una Incidencia");
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

                //Desabilitar Campos
                comboHabitacion.setDisable(true);
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    public String validarDatos() {
        String error = "";

        if (txtDescripcion.getText().trim().isEmpty()) {
            error += "El campo 'Descripcion' no puede estar vacío.\n";
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

    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }

    void cargarIncidencias() {
        ObservableList<Incidencia> listaIncidencias = DaoIncidencia.todasIncidencias();
        tablaIncidencias.setItems(listaIncidencias);
        tablaIncidencias.refresh();
    }

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
        colID.setCellValueFactory(new PropertyValueFactory<>("idIncidencia"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("habitacion"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipoIncidencia"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Configurar la columna de fecha de préstamo
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaReporte"));

        // Aplicar un formato de fecha
        colFecha.setCellFactory(column -> new TableCell<Incidencia, LocalDate>() {
            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Formatear la fecha
                    String formattedDate = item.format(formatter);
                    setText(formattedDate);
                }
            }
        });

        comboEstado.setItems(FXCollections.observableArrayList("Abierto", "Cerrado"));
        comboTipo.setItems(FXCollections.observableArrayList("Limpieza", "Mantenimiento", "Otros"));

        ObservableList<Habitacion> listaHabitaciones = DaoHabitacion.todasHabitaciones();
        comboHabitacion.getItems().addAll(listaHabitaciones);

        //Seleccionar primera opcion
        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();
        comboHabitacion.getSelectionModel().selectFirst();

        configurarEventosTabla();
        cargarIncidencias();
        estadoInicialBotones();
    }

}
