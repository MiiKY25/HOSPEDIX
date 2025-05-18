package org.hospedix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal de la aplicación Hospedix que extiende de {@link Application}.
 * Se encarga de iniciar la interfaz gráfica de usuario cargando el archivo FXML
 * correspondiente y configurando la ventana principal (Stage).
 */
public class HospedixApplication extends Application {

    /**
     * Metodo que se ejecuta al iniciar la aplicación JavaFX.
     * Carga el archivo FXML de la pantalla de inicio, configura el escenario principal
     * con título, icono, tamaño mínimo y lo muestra en pantalla.
     *
     * @param stage el escenario principal donde se mostrará la interfaz gráfica.
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HospedixApplication.class.getResource("/fxml/inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hospedix");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/logo.png")));
        stage.setMinWidth(500);
        stage.setMinHeight(300);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metodo principal que lanza la aplicación JavaFX.
     *
     * @param args argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        launch();
    }
}