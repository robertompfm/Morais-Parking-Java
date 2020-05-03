module MoraisParkingFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires fontawesomefx;

    opens view;
    opens control;
    opens model;
    opens view.fxml;
    opens view.css;
    opens model.data;
}