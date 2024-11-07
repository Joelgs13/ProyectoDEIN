package com.example.olimpiadas;

import com.example.olimpiadas.controller.PantallaPrincipalController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase {@code OlimpiadasProyecto} es la clase principal que inicia la aplicación de la interfaz gráfica
 * de usuario (GUI) basada en JavaFX para la gestión de eventos y participantes en las Olimpiadas.
 * Esta clase extiende {@link javafx.application.Application} y sirve como el punto de entrada de la aplicación.
 */
public class OlimpiadasProyecto extends Application {

    /**
     * El metodo {@code start} es el punto de entrada principal para la interfaz de usuario.
     * Este metodo se ejecuta cuando la aplicación se inicia y se encarga de cargar el archivo FXML
     * de la pantalla principal, configurar el {@link Stage}, y establecer la escena.
     *
     * @param stage El escenario principal (ventana) de la aplicación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML o cualquier recurso.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML de la pantalla principal
        FXMLLoader fxmlLoader = new FXMLLoader(OlimpiadasProyecto.class.getResource("/com/example/olimpiadas/fxml/pantalla_principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // Obtener el controlador de la pantalla principal y pasarle el Stage
        PantallaPrincipalController controller = fxmlLoader.getController();
        controller.setStage(stage);

        // Configurar el Stage (ventana)
        stage.setResizable(false); // Evitar que se redimensione la ventana
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/com/example/olimpiadas/img/iconitoOlimpiadas.png"))); // Establecer el ícono de la ventana
        stage.setTitle("Olimpiadas!"); // Establecer el título de la ventana
        stage.setScene(scene); // Establecer la escena principal
        stage.show(); // Mostrar la ventana
    }

    /**
     * El metodo {@code main} es el punto de entrada principal para la ejecución de la aplicación.
     * Llama al metodo {@link Application#launch(String...)} para iniciar la aplicación JavaFX.
     *
     * @param args Los argumentos de línea de comandos. No se utilizan en este caso.
     */
    public static void main(String[] args) {
        launch();
    }
}
