package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.ZoneId;
import org.hospedix.dao.DaoHabitacion;
import org.hospedix.dao.DaoHuesped;
import org.hospedix.dao.DaoReserva;
import org.hospedix.modelos.Habitacion;
import org.hospedix.modelos.Huesped;
import org.hospedix.modelos.Reserva;

import java.io.IOException;
import java.time.LocalDate;

public class ControllerReservas {

    @FXML public Button btnAniadir;
    @FXML private TextField txtID, txtPrecio, txtExtras, txtCantPersonas;
    @FXML private DatePicker fechaIN, fechaOUT;
    @FXML private ComboBox<Habitacion> comboHabitacion;
    @FXML private ComboBox<Huesped> comboCliente;
    @FXML private ComboBox<String> comboEstado;
    @FXML private TableView<Reserva> tablaReservas;
    @FXML private TableColumn<Reserva, Integer> colID, colHabitacion, colPersonas, colPrecio, colExtras;
    @FXML private TableColumn<Reserva, String> colCliente, colEstado;
    @FXML private TableColumn<Reserva, LocalDate> colIN, colOUT;
    @FXML private Button btnEditar, btnEliminar;

    private Reserva reservaSeleccionada = null;

    private void configurarTabla() {
        colID.setCellValueFactory(new PropertyValueFactory<>("idReserva"));
        colHabitacion.setCellValueFactory(new PropertyValueFactory<>("habitacion"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("huesped"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estadoPago"));
        colIN.setCellValueFactory(new PropertyValueFactory<>("fechaCheckin"));
        colOUT.setCellValueFactory(new PropertyValueFactory<>("fechaCheckout"));
        colPersonas.setCellValueFactory(new PropertyValueFactory<>("cantidadPersonas"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colExtras.setCellValueFactory(new PropertyValueFactory<>("extras"));
    }

    private void configurarEventos() {
        tablaReservas.setOnMouseClicked(event -> {
            reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
            if (reservaSeleccionada != null) {
                txtID.setText(String.valueOf(reservaSeleccionada.getIdReserva()));
                //comboHabitacion.setValue(reservaSeleccionada.getHabitacion());
                comboCliente.setValue(reservaSeleccionada.getHuesped());
                fechaIN.setValue(reservaSeleccionada.getFechaCheckin().toLocalDate());
                fechaOUT.setValue(reservaSeleccionada.getFechaCheckout().toLocalDate());
                comboEstado.setValue(reservaSeleccionada.getEstadoPago());
                txtCantPersonas.setText(String.valueOf(reservaSeleccionada.getCantidadPersonas()));
                txtPrecio.setText(String.valueOf(reservaSeleccionada.getPrecio()));
                txtExtras.setText(String.valueOf(reservaSeleccionada.getExtras()));
                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);

                //Desabilitar Campos
                comboHabitacion.setDisable(true);
                fechaIN.setDisable(true);
                comboCliente.setDisable(true);

                // Cargar comboBox habitacion
                Habitacion h = DaoHabitacion.buscarHabitacion(reservaSeleccionada.getHabitacion().getNumHabitacion());
                ObservableList<Habitacion> lista = FXCollections.observableArrayList(h);
                comboHabitacion.setItems(lista);
                comboHabitacion.setValue(reservaSeleccionada.getHabitacion());

            }
        });
    }

    private void cargarReservas() {
        tablaReservas.getItems().setAll(DaoReserva.todasReservas());
    }

    @FXML
    private void accionAniadir() {
        if (!validarCampos()) return;

        java.sql.Date checkin = java.sql.Date.valueOf(fechaIN.getValue());
        java.sql.Date checkout = java.sql.Date.valueOf(fechaOUT.getValue());

        Reserva r = new Reserva(
                0, // ID generado automáticamente
                comboHabitacion.getValue(),
                comboCliente.getValue(),
                checkin,
                checkout,
                comboEstado.getValue(),
                Integer.parseInt(txtCantPersonas.getText()),
                Integer.parseInt(txtPrecio.getText()),
                Integer.parseInt(txtExtras.getText())
        );

        if (DaoReserva.aniadirReserva(r)) {
            DaoHabitacion.habitacionOcupada(r.getHabitacion().getNumHabitacion());
            mostrarInfo("Reserva añadida correctamente.");
            cargarReservas();
            limpiarCampos();
        } else {
            mostrarError("Error al añadir reserva.");
        }
    }

    @FXML
    private void accionEditar() {
        if (reservaSeleccionada == null || !validarCampos()) return;

        java.sql.Date checkin = java.sql.Date.valueOf(fechaIN.getValue());
        java.sql.Date checkout = java.sql.Date.valueOf(fechaOUT.getValue());
        Reserva r = new Reserva(
                Integer.parseInt(txtID.getText()),
                comboHabitacion.getValue(),
                comboCliente.getValue(),
                checkin,
                checkout,
                comboEstado.getValue(),
                Integer.parseInt(txtCantPersonas.getText()),
                Integer.parseInt(txtPrecio.getText()),
                Integer.parseInt(txtExtras.getText())
        );


        if (DaoReserva.actualizarReserva(r)) {
            mostrarInfo("Reserva actualizada correctamente.");
            cargarReservas();
            limpiarCampos();
        } else {
            mostrarError("Error al actualizar reserva.");
        }
    }

    @FXML
    private void accionEliminar() {
        if (reservaSeleccionada == null) return;

        if (DaoReserva.eliminarReserva(reservaSeleccionada.getIdReserva())) {
            DaoHabitacion.habitacionDisponible(reservaSeleccionada.getHabitacion().getNumHabitacion());
            mostrarInfo("Reserva eliminada correctamente.");
            cargarReservas();
            limpiarCampos();
        } else {
            mostrarError("Error al eliminar reserva.");
        }
    }

    @FXML
    private void accionVolver(javafx.event.ActionEvent event) {
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

    private void limpiarCampos() {
        txtID.clear();
        comboHabitacion.getSelectionModel().clearSelection();
        comboCliente.getSelectionModel().clearSelection();
        fechaIN.setValue(null);
        fechaOUT.setValue(null);
        comboEstado.getSelectionModel().selectFirst();
        txtCantPersonas.clear();
        txtPrecio.clear();
        txtExtras.clear();
        reservaSeleccionada = null;
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
        cargarComboBox();

        //Habilitar Campos
        comboHabitacion.setDisable(false);
        fechaIN.setDisable(false);
        comboCliente.setDisable(false);
    }

    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        // Validación de selección
        if (comboHabitacion.getValue() == null) errores.append("- Debe seleccionar una habitación.\n");
        if (comboCliente.getValue() == null) errores.append("- Debe seleccionar un cliente.\n");
        if (comboEstado.getValue() == null) errores.append("- Debe seleccionar un estado de pago.\n");

        // Validación de fechas
        if (fechaIN.getValue() == null || fechaOUT.getValue() == null) {
            errores.append("- Debe ingresar ambas fechas.\n");
        } else {
            if (fechaIN.getValue().isAfter(fechaOUT.getValue())) {
                errores.append("- La fecha de entrada debe ser anterior a la fecha de salida.\n");
            }
            if (fechaIN.getValue().isBefore(LocalDate.now())) {
                errores.append("- La fecha de entrada no puede ser en el pasado.\n");
            }
        }

        // Validación de campos numéricos
        try {
            int personas = Integer.parseInt(txtCantPersonas.getText());
            if (personas <= 0 || personas > 10) {
                errores.append("- La cantidad de personas debe ser entre 1 y 10.\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- Cantidad de personas debe ser un número válido.\n");
        }

        try {
            int precio = Integer.parseInt(txtPrecio.getText());
            if (precio <= 0) {
                errores.append("- El precio debe ser mayor que 0.\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- El precio debe ser un número válido.\n");
        }

        try {
            int extras = Integer.parseInt(txtExtras.getText());
            if (extras < 0) {
                errores.append("- Los extras no pueden ser negativos.\n");
            }
        } catch (NumberFormatException e) {
            errores.append("- Los extras deben ser un número válido.\n");
        }

        // Validación de ID duplicado si se trata de un nuevo registro (no edición)
        if (txtID.getText() != null && !txtID.getText().isEmpty()) {
            try {
                int id = Integer.parseInt(txtID.getText());
                if (id <= 0) {
                    errores.append("- El ID debe ser mayor que 0.\n");
                } else if (reservaSeleccionada == null && DaoReserva.existeReservaConId(id)) {
                    errores.append("- Ya existe una reserva con este ID.\n");
                }
            } catch (NumberFormatException e) {
                errores.append("- El ID debe ser un número válido.\n");
            }
        }

        if (errores.length() > 0) {
            mostrarError("Errores encontrados:\n" + errores);
            return false;
        }

        return true;
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

    public void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    void cargarComboBox() {
        comboEstado.setItems(FXCollections.observableArrayList("Pagado", "Pendiente", "Cancelado"));
        comboHabitacion.setItems(FXCollections.observableArrayList(DaoHabitacion.todasHabitacionesDisponibles()));
        comboCliente.setItems(FXCollections.observableArrayList(DaoHuesped.todosHuesped()));

        comboEstado.getSelectionModel().selectFirst();
        comboHabitacion.getSelectionModel().selectFirst();
        comboCliente.getSelectionModel().selectFirst();
    }

    @FXML
    public void initialize() {
        txtID.setDisable(true);
        configurarTabla();
        configurarEventos();
        cargarComboBox();
        cargarReservas();
    }
}
