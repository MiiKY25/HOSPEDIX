package org.hospedix;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;

public class HelloController {
    public Label welcomeText;

    public void onHelloButtonClick(ActionEvent event) {
        welcomeText.setText("probando hola");
    }
}
