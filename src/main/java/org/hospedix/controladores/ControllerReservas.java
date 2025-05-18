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
import java.time.LocalDate;
import org.hospedix.dao.DaoHabitacion;
import org.hospedix.dao.DaoHuesped;
import org.hospedix.dao.DaoReserva;
import org.hospedix.modelos.Habitacion;
import org.hospedix.modelos.Huesped;
import org.hospedix.modelos.Reserva;

import java.io.IOException;

/**
 * Controlador para gestionar la interfaz y las operaciones relacionadas con las reservas en la aplicación.
 * Permite añadir, editar, eliminar y listar reservas, así como validar los datos ingresados y gestionar la UI.
 */
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

    /**
     * Configura las columnas de la tabla de reservas para que muestren las propiedades correspondientes de la clase Reserva.
     */
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

    /**
     * Configura el evento de selección en la tabla para cargar los datos de la reserva seleccionada en los campos del formulario.
     * También gestiona la activación y desactivación de botones y campos según el contexto.
     */
    private void configurarEventos() {
        tablaReservas.setOnMouseClicked(event -> {
            reservaSeleccionada = tablaReservas.getSelectionModel().getSelectedItem();
            if (reservaSeleccionada != null) {
                txtID.setText(String.valueOf(reservaSeleccionada.getIdReserva()));
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

                comboHabitacion.setDisable(true);
                fechaIN.setDisable(true);
                comboCliente.setDisable(true);

                Habitacion h = DaoHabitacion.buscarHabitacion(reservaSeleccionada.getHabitacion().getNumHabitacion());
                ObservableList<Habitacion> lista = FXCollections.observableArrayList(h);
                comboHabitacion.setItems(lista);
                comboHabitacion.setValue(reservaSeleccionada.getHabitacion());
            }
        });
    }

    /**
     * Carga todas las reservas desde la base de datos y las muestra en la tabla.
     */
    private void cargarReservas() {
        tablaReservas.getItems().setAll(DaoReserva.todasReservas());
    }

    /**
     * Acción para añadir una nueva reserva con los datos ingresados en el formulario.
     * Valida los campos antes de intentar guardar.
     */
    @FXML
    private void accionAniadir() {
        if (!validarCampos()) return;

        java.sql.Date checkin = java.sql.Date.valueOf(fechaIN.getValue());
        java.sql.Date checkout = java.sql.Date.valueOf(fechaOUT.getValue());

        Reserva r = new Reserva(
                0,
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

    /**
     * Acción para editar una reserva seleccionada con los datos actuales del formulario.
     * Valida los campos antes de intentar actualizar.
     */
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

    /**
     * Acción para eliminar la reserva seleccionada tras confirmación del usuario.
     */
    @FXML
    private void accionEliminar() {
        if (reservaSeleccionada == null) {
            mostrarError("Selecciona una reserva para eliminar.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Estás seguro de que deseas eliminar esta reserva?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        ButtonType btnSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
        ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
        confirm.getButtonTypes().setAll(btnSi, btnNo);

        confirm.showAndWait().ifPresent(respuesta -> {
            if (respuesta == btnSi) {
                if (DaoReserva.eliminarReserva(reservaSeleccionada.getIdReserva())) {
                    DaoHabitacion.habitacionDisponible(reservaSeleccionada.getHabitacion().getNumHabitacion());
                    mostrarInfo("Reserva eliminada correctamente.");
                    cargarReservas();
                    limpiarCampos();
                } else {
                    mostrarError("Error al eliminar reserva.");
                }
            }
        });
    }

    /**
     * Acción para volver al menú principal desde la interfaz actual.
     *
     * @param event evento disparado por el botón de volver.
     */
    @FXML
    private void accionVolver(ActionEvent event) {
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
     * Limpia todos los campos del formulario y restablece el estado de la interfaz.
     */
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

        comboHabitacion.setDisable(false);
        fechaIN.setDisable(false);
        comboCliente.setDisable(false);
    }

    /**
     * Valida los campos del formulario asegurando que los datos ingresados son correctos.
     *
     * @return true si todos los campos son válidos; false en caso contrario.
     */
    private boolean validarCampos() {
        StringBuilder errores = new StringBuilder();

        if (comboHabitacion.getValue() == null) errores.append("- Debe seleccionar una habitación.\n");
        if (comboCliente.getValue() == null) errores.append("- Debe seleccionar un cliente.\n");
        if (comboEstado.getValue() == null) errores.append("- Debe seleccionar un estado de pago.\n");

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
     * Muestra un mensaje de error en un cuadro de diálogo.
     *
     * @param error mensaje de error a mostrar.
     */
    void mostrarError(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(error);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje informativo en un cuadro de diálogo.
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
     * Limpia los campos del formulario al activar la acción de limpiar.
     *
     * @param event evento disparado por el botón limpiar.
     */
    public void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Carga los datos en los ComboBox de la interfaz.
     */
    void cargarComboBox() {
        comboEstado.setItems(FXCollections.observableArrayList("Pagado", "Pendiente", "Cancelado"));
        comboHabitacion.setItems(FXCollections.observableArrayList(DaoHabitacion.todasHabitacionesDisponibles()));
        comboCliente.setItems(FXCollections.observableArrayList(DaoHuesped.todosHuesped()));

        comboEstado.getSelectionModel().selectFirst();
        comboHabitacion.getSelectionModel().selectFirst();
        comboCliente.getSelectionModel().selectFirst();
    }

    /**
     * Inicializa el controlador, configurando la tabla, los eventos, y cargando los datos iniciales.
     */
    @FXML
    public void initialize() {
        txtID.setDisable(true);
        configurarTabla();
        configurarEventos();
        cargarComboBox();
        cargarReservas();
    }
}
