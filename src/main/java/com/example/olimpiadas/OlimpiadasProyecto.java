package com.example.olimpiadas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class OlimpiadasProyecto extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OlimpiadasProyecto.class.getResource("/com/example/olimpiadas/fxml/pantalla_principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/olimpiadas/img/iconitoOlimpiadas.png")));
        stage.setTitle("Olimpiadas!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}