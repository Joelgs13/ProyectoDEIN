module com.example.olimpiadas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports com.example.olimpiadas.controller to javafx.fxml;
    opens com.example.olimpiadas to javafx.fxml;
    opens com.example.olimpiadas.controller to javafx.fxml;
    exports com.example.olimpiadas;
}
