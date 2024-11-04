module com.example.olimpiadas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.olimpiadas to javafx.fxml;
    exports com.example.olimpiadas;
}