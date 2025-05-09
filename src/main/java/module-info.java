module org.hospedix {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.hospedix.controladores to javafx.fxml;
    opens org.hospedix.modelos to javafx.base;
    opens org.hospedix to javafx.fxml;
    exports org.hospedix;
}