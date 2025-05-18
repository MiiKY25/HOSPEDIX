package org.hospedix.controladores;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import java.util.Optional;

/**
 * Controlador para la gestión de habitaciones.
 * Permite añadir, editar, eliminar y listar habitaciones en la interfaz.
 * También valida datos ingresados y maneja interacciones con la tabla y los formularios.
 */
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

    /**
     * Habitacion actualmente seleccionada en la tabla.
     */
    private Habitacion habitacionSeleccionada = null;

    /**
     * Configura los eventos de la tabla para que al hacer click en una fila,
     * se carguen los datos en el formulario para editar o eliminar.
     */
    private void configurarEventosTabla() {
        tablaHabitacion.setOnMouseClicked(event -> {
            habitacionSeleccionada = tablaHabitacion.getSelectionModel().getSelectedItem();

            if (habitacionSeleccionada != null) {
                txtHabitacion.setText(String.valueOf(habitacionSeleccionada.getNumHabitacion()));
                txtPrecio.setText(String.valueOf(habitacionSeleccionada.getPrecio()));

                comboEstado.getSelectionModel().clearSelection();
                comboEstado.getSelectionModel().select(habitacionSeleccionada.getEstado());

                comboTipo.getSelectionModel().clearSelection();
                comboTipo.getSelectionModel().select(habitacionSeleccionada.getTipo());

                txtHabitacion.setDisable(true);

                btnEditar.setDisable(false);
                btnEliminar.setDisable(false);
                btnAniadir.setDisable(true);
            }
        });
    }

    /**
     * Carga todas las habitaciones desde la base de datos y actualiza la tabla.
     */
    private void cargarHabitaciones() {
        tablaHabitacion.getItems().setAll(DaoHabitacion.todasHabitaciones());
    }

    /**
     * Acción para añadir una nueva habitación.
     * Valida los datos y muestra mensajes de éxito o error.
     *
     * @param event Evento de acción al pulsar el botón Añadir.
     */
    @FXML
    void accionAniadir(ActionEvent event) {
        if (!validarCampos()) return;

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

    /**
     * Acción para editar la habitación seleccionada.
     * Valida y actualiza los datos en la base de datos.
     *
     * @param event Evento de acción al pulsar el botón Editar.
     */
    @FXML
    void accionEditar(ActionEvent event) {
        if (habitacionSeleccionada == null) {
            mostrarError("Seleccione una habitación para editar.");
            return;
        }

        if (!validarCampos()) return;

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
                estadoInicialBotones();
            } else {
                mostrarError("Error al actualizar la habitación.");
            }
        } catch (NumberFormatException e) {
            mostrarError("Precio inválido.");
        }
    }

    /**
     * Acción para eliminar la habitación seleccionada.
     * Confirma eliminación, verifica si hay registros asociados y elimina en consecuencia.
     *
     * @param event Evento de acción al pulsar el botón Eliminar.
     */
    @FXML
    void accionEliminar(ActionEvent event) {
        if (habitacionSeleccionada == null) {
            mostrarError("Seleccione una habitación para eliminar.");
            return;
        }

        int numHabitacion = habitacionSeleccionada.getNumHabitacion();
        boolean tieneReservas = DaoHabitacion.habitacionTieneReservas(numHabitacion);
        boolean tieneIncidencias = DaoHabitacion.habitacionTieneIncidencias(numHabitacion);

        if (tieneReservas || tieneIncidencias) {
            String mensaje = "La habitación está vinculada a:\n";
            if (tieneReservas) mensaje += "- Reservas\n";
            if (tieneIncidencias) mensaje += "- Incidencias\n";
            mensaje += "\nSi continúas, se eliminarán todos estos registros.\n¿Deseas continuar?";

            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación de eliminación");
            alerta.setHeaderText("Esta habitación tiene registros asociados");
            alerta.setContentText(mensaje);

            ButtonType btnSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            alerta.getButtonTypes().setAll(btnSi, btnNo);

            alerta.showAndWait().ifPresent(respuesta -> {
                if (respuesta == btnSi) {
                    if (DaoHabitacion.eliminarHabitacionYAsociados(numHabitacion)) {
                        mostrarInfo("Habitación y registros asociados eliminados correctamente.");
                        limpiarCampos();
                        cargarHabitaciones();
                        estadoInicialBotones();
                    } else {
                        mostrarError("No se pudo eliminar la habitación.");
                    }
                }
            });
        } else {
            // Eliminación simple si no tiene vínculos
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Eliminar habitación");
            confirm.setHeaderText("¿Estás seguro?");
            confirm.setContentText("Esta acción no se puede deshacer.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (DaoHabitacion.eliminarHabitacion(numHabitacion)) {
                    mostrarInfo("Habitación eliminada correctamente.");
                    limpiarCampos();
                    cargarHabitaciones();
                    estadoInicialBotones();
                } else {
                    mostrarError("No se pudo eliminar la habitación.");
                }
            }
        }
    }

    /**
     * Limpia los campos del formulario.
     *
     * @param event Evento de acción al pulsar el botón Limpiar.
     */
    @FXML
    void accionLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Vuelve al menú principal.
     *
     * @param event Evento de acción al pulsar el botón Volver.
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
     * Valida que los campos del formulario estén completos y con formato correcto.
     *
     * @return true si los datos son válidos; false si hay algún error.
     */
    private boolean validarCampos() {
        String habitacionStr = txtHabitacion.getText().trim();
        String precioStr = txtPrecio.getText().trim();
        String estado = comboEstado.getValue();
        String tipo = comboTipo.getValue();

        if (habitacionStr.isEmpty() || precioStr.isEmpty() || estado == null || tipo == null) {
            mostrarError("Todos los campos son obligatorios.");
            return false;
        }

        try {
            Integer.parseInt(habitacionStr);
            Double.parseDouble(precioStr);
        } catch (NumberFormatException e) {
            mostrarError("Número de habitación y precio deben ser válidos.");
            return false;
        }

        return true;
    }

    /**
     * Muestra un mensaje de error en una alerta.
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
     * Muestra un mensaje informativo en una alerta.
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
     * Limpia los campos del formulario y restablece el estado inicial de los botones.
     */
    private void limpiarCampos() {
        txtHabitacion.clear();
        txtPrecio.clear();
        comboEstado.getSelectionModel().selectFirst();
        comboTipo.getSelectionModel().selectFirst();
        habitacionSeleccionada = null;
        txtHabitacion.setDisable(false);
        btnAniadir.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
    }

    /**
     * Establece el estado inicial de los botones (añadir habilitado, editar y eliminar deshabilitados).
     */
    private void estadoInicialBotones() {
        btnEditar.setDisable(true);
        btnEliminar.setDisable(true);
        btnAniadir.setDisable(false);
    }

    /**
     * Metodo de inicialización de JavaFX.
     * Configura las columnas de la tabla, los combos, carga datos y establece el estado inicial.
     */
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
}
