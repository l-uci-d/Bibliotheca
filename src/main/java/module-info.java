module com.example.oopapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;

    opens com.example.oopapplication to javafx.fxml;
    exports com.example.oopapplication;
}