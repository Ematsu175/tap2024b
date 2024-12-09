module com.example.tap2024b {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;


    opens com.example.tap2024b to javafx.fxml;
    opens com.example.tap2024b.vistas to javafx.base;
    exports com.example.tap2024b;
    requires org.kordamp.bootstrapfx.core;
    requires mysql.connector.j;
    requires java.sql;
    requires itextpdf;
    opens com.example.tap2024b.models;
}