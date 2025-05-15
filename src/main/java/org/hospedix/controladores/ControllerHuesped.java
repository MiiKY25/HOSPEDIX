package org.hospedix.controladores;

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
import org.hospedix.dao.DaoHuesped;
import org.hospedix.modelos.Empleado;
import org.hospedix.modelos.Huesped;

import java.io.IOException;
import java.util.Optional;

public class ControllerHuesped {

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnAniadir;

    @FXML
    private TableColumn<Huesped, String> colApellidos;

    @FXML
    private TableColumn<Huesped, String> colDNI;

    @FXML
    private TableColumn<Huesped, String> colNombre;

    @FXML
    private TableColumn<Huesped, Integer> colTelefono;

    @FXML
    private TableView<Huesped> tablaHuesped;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtDNI;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    private Huesped huespedSeleccionado = null;

    @FXML
    void accionAniadir(ActionEvent event) {
        String error=validarDatos();
        if (error.isEmpty()){
            Huesped huesped= DaoHuesped.huespedDNI(txtDNI.getText());
            if (huesped==null){
                //Crear Huesped
                Huesped h=new Huesped(txtDNI.getText(),txtNombre.getText(),txtApellidos.getText(),Integer.parseInt(txtTelefono.getText()));
                Boolean estado = DaoHuesped.aniadirHuesped(h);
                if (estado) {
                    mostrarInfo("Huesped creado correctamente");
                    limpiarCampos();
                    cargarHuesped();
                } else {
                    mostrarError("Error al crear el Huesped");
                }
            }else {
                mostrarError("Ya existe un huesped con el DNI: "+txtDNI.getText());
            }
        }else {
            mostrarError(error);
        }
    }

    @FXML
    void accionEditar(ActionEvent event) {
        Huesped h=tablaHuesped.getSelectionModel().getSelectedItem();
        if (h!=null){
            String error=validarDatos();
            if (error.isEmpty()){
                Huesped HuespedNuevo=new Huesped(h.getDni(),txtNombre.getText(),txtApellidos.getText(),Integer.parseInt(txtTelefono.getText()));
                Boolean estado = DaoHuesped.actualizarHuesped(HuespedNuevo);
                if (estado) {
                    mostrarInfo("Huesped editado correctamente");
                    limpiarCampos();
                    cargarHuesped();
                    estadoInicialBotones();
                } else {
                    mostrarError("Error al editar el Huesped");
                }
            }else{
                mostrarError(error);
            }
        }else {
            mostrarError("Selecciona un Huesped");
        }
    }

    @FXML
    private void accionEliminar() {
        Huesped seleccionado = tablaHuesped.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ningún huésped seleccionado", "Por favor selecciona un huésped para eliminar.");
            return;
        }

        String dni = seleccionado.getDni();

        if (DaoHuesped.huespedTieneReservas(dni)) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmar eliminación");
            confirm.setHeaderText("Este huésped tiene reservas vinculadas.");
            confirm.setContentText("Si continúas, también se eliminarán todas sus reservas.\n¿Deseas continuar?");

            ButtonType btnSi = new ButtonType("Sí", ButtonBar.ButtonData.YES);
            ButtonType btnNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            confirm.getButtonTypes().setAll(btnSi, btnNo);

            Optional<ButtonType> resultado = confirm.showAndWait();
            if (resultado.isPresent() && resultado.get() == btnSi) {
                if (DaoHuesped.eliminarHuespedYReservas(dni)) {
                    tablaHuesped.setItems(DaoHuesped.todosHuesped());
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Huésped y reservas eliminados correctamente.");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el huésped.");
                }
            } else {
                // Cancelado por el usuario
                return;
            }
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Eliminar huésped");
            confirm.setHeaderText("¿Estás seguro?");
            confirm.setContentText("Esta acción no se puede deshacer.");
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (DaoHuesped.eliminarHuesped(dni)) {
                    tablaHuesped.setItems(DaoHuesped.todosHuesped());
                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Huésped eliminado correctamente.");
                } else {
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el huésped.");
                }
            }
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

    void cargarHuesped() {
        ObservableList<Huesped> listaHuesped = DaoHuesped.todosHuesped();
        tablaHuesped.setItems(listaHuesped);
        tablaHuesped.refresh();
    }

    private void configurarEventosTabla() {
        tablaHuesped.setOnMouseClicked(event -> {
            huespedSeleccionado = tablaHuesped.getSelectionModel().getSelectedItem();

            if (huespedSeleccionado != null) {
                txtDNI.setText(String.valueOf(huespedSeleccionado.getDni()));
                txtTelefono.setText(String.valueOf(huespedSeleccionado.getTelefono()));
                txtApellidos.setText(String.valueOf(huespedSeleccionado.getApellido()));
                txtNombre.setText(String.valueOf(huespedSeleccionado.getNombre()));
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
        txtDNI.setDisable(false);
        btnAniadir.setDisable(false);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
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

        // Validar teléfono
        String telefono = txtTelefono.getText().trim();
        if (telefono.isEmpty()) {
            error += "El campo 'Teléfono' no puede estar vacío.\n";
        } else if (!telefono.matches("\\d{9}")) {
            error += "El teléfono debe tener exactamente 9 dígitos numéricos.\n";
        }

        return error;
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void initialize() {
        // Configuración de las columnas de la tabla
        colDNI.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colApellidos.setCellValueFactory(new PropertyValueFactory<>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        configurarEventosTabla();
        cargarHuesped();
        estadoInicialBotones();
    }
}
