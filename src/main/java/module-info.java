module com.example.oopapplication {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.example.oopapplication to javafx.fxml;
    exports com.example.oopapplication;
}