package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import org.hospedix.dao.DaoHabitacion;
import org.hospedix.modelos.Habitacion;

import java.io.IOException;

public class ControllerHabitaciones {

    @FXML private Button btnAniadir;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private TableColumn<Habitacion, String> colEstado;
    @FXML private TableColumn<Habitacion, Integer> colHabitacion;
    @FXML private TableColumn<Habitacion, Double> colPrecio;
    @FXML private TableColumn<Habitacion, String> colTipo;
    @FXML private ComboBox<String> comboEstado;
    @FXML private ComboBox<String> comboTipo;
    @FXML private TableView<Habitacion> tablaHabitacion;
    @FXML private TextField txtHabitacion;
    @FXML private TextField txtPrecio;

    private Habitacion habitacionSeleccionada = null;

    @FXML
    private void initialize() {
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("numHabitacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        comboEstado.setItems(FXCollections.observableArrayList("Disponible", "Ocupada", "Mantenimiento"));
        comboTipo.setItems(FXCollections.observableArrayList("Individual", "Doble", "Suite"));
        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();

        configurarEventosTabla();
        cargarHabitaciones();
        estadoInicialBotones();
    }

    private void configurarEventosTabla() {
        tablaHabitacion.setOnMouseClicked(event -> {
            habitacionSeleccionada = tablaHabitacion.getSelectionModel().getSelectedItem();

            if (habitacionSeleccionada != null) {
                txtHabitacion.setText(String.valueOf(habitacionSeleccionada.getNumHabitacion()));
                txtPrecio.setText(String.valueOf(habitacionSeleccionada.getPrecio()));
                comboEstado.setValue(habitacionSeleccionada.getEstado());
                comboTipo.setValue(habitacionSeleccionada.getTipo());
                txtHabitacion.setDisable(true);

                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    private void cargarHabitaciones() {
        tablaHabitacion.setItems(DaoHabitacion.todasHabitaciones());
        tablaHabitacion.getItems().setAll(DaoHabitacion.todasHabitaciones());
    }

    @FXML
    void accionAniadir(javafx.event.ActionEvent event) {
        try {
            int num = Integer.parseInt(txtHabitacion.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            String estado = comboEstado.getValue();
            String tipo = comboTipo.getValue();

            if (DaoHabitacion.buscarHabitacion(num) != null) {
                mostrarError("Ya existe una habitación con ese número.");
                return;
            }

            Habitacion h = new Habitacion(num, estado, tipo, precio);
            boolean insertado = DaoHabitacion.aniadirHabitacion(h);

            if (insertado) {
                mostrarInfo("Habitación añadida correctamente.");
                limpiarCampos();
                cargarHabitaciones();
            } else {
                mostrarError("Error al insertar la habitación.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Número de habitación o precio no válido.");
        }
    }

    @FXML
    void accionEditar(javafx.event.ActionEvent event) {
        if (habitacionSeleccionada == null) {
            mostrarError("Seleccione una habitación para editar.");
            return;
        }

        try {
            double nuevoPrecio = Double.parseDouble(txtPrecio.getText().trim());
            String nuevoEstado = comboEstado.getValue();
            String nuevoTipo = comboTipo.getValue();

            Habitacion nuevaHabitacion = new Habitacion(
                    habitacionSeleccionada.getNumHabitacion(),
                    nuevoEstado,
                    nuevoTipo,
                    nuevoPrecio
            );

            boolean actualizado = DaoHabitacion.actualizarHabitacion(nuevaHabitacion);
            if (actualizado) {
                mostrarInfo("Habitación actualizada correctamente.");
                limpiarCampos();
                cargarHabitaciones();
                estadoInicialBotones(); // ← Aquí se vuelven a ajustar los botones
            } else {
                mostrarError("Error al actualizar la habitación.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Precio inválido.");
        }
    }


    @FXML
    void accionEliminar(javafx.event.ActionEvent event) {
        if (habitacionSeleccionada == null) {
            mostrarError("Seleccione una habitación para eliminar.");
            return;
        }

        boolean eliminado = DaoHabitacion.eliminarHabitacion(habitacionSeleccionada.getNumHabitacion());
        if (eliminado) {
            mostrarInfo("Habitación eliminada correctamente.");
            limpiarCampos();
            cargarHabitaciones();
            estadoInicialBotones(); // ← Aquí también
        } else {
            mostrarError("Error al eliminar la habitación.");
        }
    }


    @FXML
    void accionVolver(javafx.event.ActionEvent event) {
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

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void limpiarCampos() {
        txtHabitacion.clear();
        txtPrecio.clear();
        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();
        habitacionSeleccionada = null;
        txtHabitacion.setDisable(false);
    }

    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }
}
