module MoraisParkingFX {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens view;
    opens control;
}