module org.hospedix {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.hospedix to javafx.fxml;
    exports org.hospedix;
}