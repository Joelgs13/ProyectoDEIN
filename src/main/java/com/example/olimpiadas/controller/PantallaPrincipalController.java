package com.example.olimpiadas.controller;

import com.example.olimpiadas.DAO.DeporteDAO;
import com.example.olimpiadas.DAO.OlimpiadaDAO;
import com.example.olimpiadas.DAO.DeportistaDAO;
import com.example.olimpiadas.DAO.EquipoDAO;
import com.example.olimpiadas.DAO.EventoDAO;
import com.example.olimpiadas.DAO.ParticipacionDAO;
import com.example.olimpiadas.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

/**
 * El controlador principal de la aplicación, que maneja la lógica y las interacciones de la interfaz de usuario.
 * Este controlador está asociado con la pantalla principal de la aplicación de las Olimpiadas.
 * Se encarga de gestionar las diferentes acciones de los menús y de interactuar con las tablas de la interfaz.
 */
public class PantallaPrincipalController {

    /**
     * Elementos de menú para agregar nuevas entidades a la base de datos.
     */
    @FXML
    private MenuItem miAniadirDeporte;

    @FXML
    private MenuItem miAniadirDeportista;

    @FXML
    private MenuItem miAniadirEquipo;

    @FXML
    private MenuItem miAniadirEvento;

    @FXML
    private MenuItem miAniadirOlimpiada;

    @FXML
    private MenuItem miAniadirParticipacion;

    /**
     * Elementos de menú para eliminar entidades existentes de la base de datos.
     */
    @FXML
    private MenuItem miBorrarDeporte;

    @FXML
    private MenuItem miBorrarDeportista;

    @FXML
    private MenuItem miBorrarEquipo;

    @FXML
    private MenuItem miBorrarEvento;

    @FXML
    private MenuItem miBorrarOlimpiada;

    @FXML
    private MenuItem miBorrarParticipacion;

    /**
     * Elementos de menú para editar las entidades existentes en la base de datos.
     */
    @FXML
    private MenuItem miEditarDeporte;

    @FXML
    private MenuItem miEditarDeportista;

    @FXML
    private MenuItem miEditarEquipo;

    @FXML
    private MenuItem miEditarEvento;

    @FXML
    private MenuItem miEditarOlimpiada;

    @FXML
    private MenuItem miEditarParticipacion;

    /**
     * Tabla que muestra los registros de la base de datos.
     */
    @FXML
    public TableView tabla;

    /**
     * Obtiene la instancia de la tabla.
     *
     * @return La tabla que se utiliza en la interfaz de usuario.
     */
    public TableView getTabla() {
        return this.tabla;
    }

    /**
     * Campo de texto para ingresar el nombre de una entidad (por ejemplo, nombre de un deporte o deportista).
     */
    @FXML
    private TextField tfNombre;

    /**
     * ComboBox para seleccionar la tabla que se desea visualizar (Deporte, Deportista, Equipo, etc.).
     */
    @FXML
    private ComboBox<String> cbTablaElegida;

    /**
     * El escenario (ventana) de la aplicación. Es usado para gestionar el ciclo de vida de la ventana.
     */
    private Stage stage;


    /**
     * Metodo para recibir el escenario (Stage) desde la clase principal.
     * Este metodo es utilizado para configurar la ventana principal desde fuera de este controlador.
     *
     * @param stage El escenario (ventana) que se va a asociar con este controlador.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Metodo que se ejecuta al inicializar la pantalla principal.
     * Se configuran las opciones del ComboBox para seleccionar qué tabla mostrar,
     * y se llaman a métodos para generar deportes, configurar el comportamiento del doble clic en la tabla
     * y gestionar el evento de la tecla Escape.
     */
    @FXML
    public void initialize() {
        ObservableList<String> tablas = FXCollections.observableArrayList(
                "Deporte", "Deportistas", "Equipos", "Eventos", "Olimpiadas", "Participaciones"
        );
        cbTablaElegida.setItems(tablas);
        cbTablaElegida.getSelectionModel().selectFirst(); // Selecciona el primer elemento por defecto
        generarDeportes();
        configurarDobleClicTabla();
        configurarEscapeTecla();
    }

    /**
     * Configura el comportamiento de la tabla para detectar un doble clic en las filas.
     * Cuando un usuario realiza un doble clic en una fila, se llama al metodo adecuado para editar el objeto seleccionado.
     */
    private void configurarDobleClicTabla() {
        tabla.setRowFactory(tv -> {
            TableRow<Object> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !fila.isEmpty()) {
                    Object objetoSeleccionado = fila.getItem();
                    manejarDobleClic(objetoSeleccionado);
                }
            });
            return fila;
        });
    }

    /**
     * Maneja la acción de editar un objeto cuando se hace doble clic en él.
     * Dependiendo del tipo de objeto seleccionado (Deporte, Deportista, Evento, etc.),
     * se llama al metodo correspondiente para editarlo.
     *
     * @param objeto El objeto que se seleccionó para editar.
     */
    private void manejarDobleClic(Object objeto) {
        if (objeto instanceof Deporte) {
            editarDeporte(null);
        } else if (objeto instanceof Deportista) {
            editarDeportista(null);
        } else if (objeto instanceof Evento) {
            editarEvento(null);
        } else if (objeto instanceof Equipo) {
            editarEquipo(null);
        } else if (objeto instanceof Olimpiada) {
            editarOlimpiada(null);
        } else if (objeto instanceof Participacion) {
            editarParticipacion(null);
        } else {
            showError("Tipo de objeto no soportado para edición.");
        }
    }

    /**
     * Configura el comportamiento cuando se presiona la tecla Escape en la tabla.
     * Si hay un objeto seleccionado en la tabla, se llama al metodo correspondiente para eliminarlo.
     */
    private void configurarEscapeTecla() {
        tabla.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE && tabla.getSelectionModel().getSelectedItem() != null) {
                Object objetoSeleccionado = tabla.getSelectionModel().getSelectedItem();
                manejarEliminar(objetoSeleccionado);
            }
        });
    }

    /**
     * Maneja la eliminación del objeto seleccionado en la tabla.
     * Dependiendo del tipo de objeto seleccionado (Deporte, Deportista, Evento, etc.),
     * se llama al metodo correspondiente para eliminarlo.
     *
     * @param objeto El objeto que se desea eliminar.
     */
    private void manejarEliminar(Object objeto) {
        if (objeto instanceof Deporte) {
            borrarDeporte(null);
        } else if (objeto instanceof Deportista) {
            borrarDeportista(null);
        } else if (objeto instanceof Evento) {
            borrarEvento(null);
        } else if (objeto instanceof Equipo) {
            borrarEquipo(null);
        } else if (objeto instanceof Olimpiada) {
            borrarOlimpiada(null);
        } else if (objeto instanceof Participacion) {
            borrarParticipacion(null);
        } else {
            showError("Tipo de objeto no soportado para eliminación.");
        }
    }

    /**
     * Muestra un mensaje de error en una ventana emergente (Alert).
     * Este metodo se usa para mostrar un mensaje cuando ocurre un error en el sistema.
     *
     * @param mensaje El mensaje de error que se va a mostrar al usuario.
     */
    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    /**
     * Abre una ventana modal para agregar un nuevo Deporte.
     * Este metodo carga el FXML de la ventana para agregar un deporte y configura la interacción con el controlador de esa ventana.
     *
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    public void aniadirDeporte() {
        try {
            // Cargar el FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deporte.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            DeporteController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Deporte");
            stage.showAndWait();
            cambiarDeTabla(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre una ventana modal para agregar un nuevo Deportista.
     * Este metodo carga el FXML de la ventana para agregar un deportista y configura la interacción con el controlador de esa ventana.
     *
     * @param event El evento generado al hacer clic en el botón para agregar un deportista.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    void aniadirDeportista(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Deportista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deportista.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            DeportistaController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Deportista");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre una ventana modal para agregar un nuevo Equipo.
     * Este metodo carga el FXML de la ventana para agregar un equipo y configura la interacción con el controlador de esa ventana.
     *
     * @param event El evento generado al hacer clic en el botón para agregar un equipo.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    void aniadirEquipo(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Equipo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/equipo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            EquipoController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Equipo");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre una ventana modal para agregar un nuevo Evento.
     * Este metodo carga el FXML de la ventana para agregar un evento y configura la interacción con el controlador de esa ventana.
     *
     * @param event El evento generado al hacer clic en el botón para agregar un evento.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    void aniadirEvento(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Evento
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/evento.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            EventoController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Evento");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre una ventana modal para agregar una nueva Olimpiada.
     * Este metodo carga el FXML de la ventana para agregar una olimpiada y configura la interacción con el controlador de esa ventana.
     *
     * @param event El evento generado al hacer clic en el botón para agregar una olimpiada.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    void aniadirOlimpiada(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Olimpiada
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/olimpiada.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            OlimpiadaController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Olimpiada");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Abre una ventana modal para agregar una nueva Participación.
     * Este metodo carga el FXML de la ventana para agregar una participación y configura la interacción con el controlador de esa ventana.
     *
     * @param event El evento generado al hacer clic en el botón para agregar una participación.
     * @throws IOException Si ocurre un error al cargar el archivo FXML.
     */
    @FXML
    void aniadirParticipacion(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Participación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/participacion.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            ParticipacionController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Participación");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Elimina un deporte seleccionado de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona un deporte en la tabla y el usuario confirma la eliminación, el deporte será eliminado.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ningún deporte, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar un deporte.
     */
    @FXML
    void borrarDeporte(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            Deporte deporteSeleccionado = (Deporte) tabla.getSelectionModel().getSelectedItem();

            if (deporteSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este deporte?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = DeporteDAO.deleteDeporte(deporteSeleccionado.getIdDeporte());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El deporte ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el deporte.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un deporte para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto deporte.");
            warningAlert.showAndWait();
        }
    }

    /**
     * Elimina un deportista seleccionado de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona un deportista en la tabla y el usuario confirma la eliminación, el deportista será eliminado.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ningún deportista, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar un deportista.
     */
    @FXML
    void borrarDeportista(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) { // Verificar que la selección sea "Deportistas"
            Deportista deportistaSeleccionado = (Deportista) tabla.getSelectionModel().getSelectedItem();

            if (deportistaSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este deportista?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = DeportistaDAO.deleteDeportista(deportistaSeleccionado.getIdDeportista());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El deportista ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el deportista.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un deportista para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto deportista.");
            warningAlert.showAndWait();
        }
    }

    /**
     * Elimina un equipo seleccionado de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona un equipo en la tabla y el usuario confirma la eliminación, el equipo será eliminado.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ningún equipo, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar un equipo.
     */
    @FXML
    void borrarEquipo(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            Equipo equipoSeleccionado = (Equipo) tabla.getSelectionModel().getSelectedItem();

            if (equipoSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este equipo?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = EquipoDAO.deleteEquipo(equipoSeleccionado.getIdEquipo());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El equipo ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el equipo.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un equipo para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto equipo.");
            warningAlert.showAndWait();
        }
    }



    /**
     * Elimina un evento seleccionado de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona un evento en la tabla y el usuario confirma la eliminación, el evento será eliminado.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ningún evento, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar un evento.
     */
    @FXML
    void borrarEvento(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            Evento eventoSeleccionado = (Evento) tabla.getSelectionModel().getSelectedItem();

            if (eventoSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este evento?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = EventoDAO.deleteEvento(eventoSeleccionado.getIdEvento());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El evento ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el evento.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un evento para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto evento.");
            warningAlert.showAndWait();
        }
    }

    /**
     * Elimina una olimpiada seleccionada de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona una olimpiada en la tabla y el usuario confirma la eliminación, la olimpiada será eliminada.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ninguna olimpiada, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar una olimpiada.
     */
    @FXML
    void borrarOlimpiada(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Olimpiadas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            Olimpiada olimpiadaSeleccionada = (Olimpiada) tabla.getSelectionModel().getSelectedItem();

            if (olimpiadaSeleccionada != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar esta olimpiada?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Eliminar la olimpiada utilizando el DAO
                    boolean exito = OlimpiadaDAO.deleteOlimpiada(olimpiadaSeleccionada.getIdOlimpiada());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La olimpiada ha sido eliminada.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar la olimpiada.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una olimpiada
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona una olimpiada para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            // Mostrar mensaje si la tabla seleccionada no es de Olimpiadas
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona una olimpiada.");
            warningAlert.showAndWait();
        }
    }

    /**
     * Elimina una participación seleccionada de la tabla después de confirmar la acción con el usuario.
     * Si se selecciona una participación en la tabla y el usuario confirma la eliminación, la participación será eliminada.
     * Después de la eliminación, la tabla se actualizará y se mostrará un mensaje de éxito o error según corresponda.
     * Si no se selecciona ninguna participación, se mostrará una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para eliminar una participación.
     */
    @FXML
    void borrarParticipacion(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Participaciones")) {
            Participacion participacionSeleccionada = (Participacion) tabla.getSelectionModel().getSelectedItem();

            if (participacionSeleccionada != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar esta participación?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = ParticipacionDAO.deleteParticipacion(
                            participacionSeleccionada.getDeportista().getIdDeportista(),
                            participacionSeleccionada.getEvento().getIdEvento()
                    );
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La participación ha sido eliminada.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar la participación.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona una participación para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto participación.");
            warningAlert.showAndWait();
        }
    }



    /**
     * Abre una ventana modal para editar un deporte seleccionado de la tabla.
     * Si un deporte es seleccionado y el usuario confirma la acción, se abre una nueva ventana de edición.
     * Después de la edición, la tabla se actualiza con los nuevos datos.
     * Si no se selecciona ningún deporte, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar un deporte.
     */
    @FXML
    void editarDeporte(ActionEvent event) {
        // Obtener el deporte seleccionado de la tabla
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            Deporte deporteSeleccionado = (Deporte) tabla.getSelectionModel().getSelectedItem();

            if (deporteSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deporte.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    DeporteController controller = loader.getController();

                    // Pasar el deporte seleccionado al controlador para editar
                    controller.setDeporte(deporteSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Deporte");
                    stage.showAndWait();
                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un deporte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un deporte para editar.");
                alert.showAndWait();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un deporte.");
            alert.showAndWait();
        }

    }

    /**
     * Abre una ventana modal para editar un deportista seleccionado de la tabla.
     * Si un deportista es seleccionado y el usuario confirma la acción, se abre una nueva ventana de edición.
     * Después de la edición, la tabla se actualiza con los nuevos datos.
     * Si no se selecciona ningún deportista, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar un deportista.
     */
    @FXML
    void editarDeportista(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Deportistas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            Deportista deportistaSeleccionado = (Deportista) tabla.getSelectionModel().getSelectedItem();

            if (deportistaSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deportista.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    DeportistaController controller = loader.getController();

                    // Pasar el deportista seleccionado al controlador para editar
                    controller.setDeportista(deportistaSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Deportista");
                    stage.showAndWait();
                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un deportista
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un deportista para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un deportista.");
            alert.showAndWait();
        }
    }

    /**
     * Abre una ventana modal para editar un equipo seleccionado de la tabla.
     * Si un equipo es seleccionado y el usuario confirma la acción, se abre una nueva ventana de edición.
     * Después de la edición, la tabla se actualiza con los nuevos datos.
     * Si no se selecciona ningún equipo, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar un equipo.
     */
    @FXML
    void editarEquipo(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Equipos
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            Equipo equipoSeleccionado = (Equipo) tabla.getSelectionModel().getSelectedItem();

            if (equipoSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar el equipo
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/equipo.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    EquipoController controller = loader.getController();

                    // Pasar el equipo seleccionado al controlador para editar
                    controller.setEquipo(equipoSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Equipo");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un equipo
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un equipo para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un equipo.");
            alert.showAndWait();
        }
    }



    /**
     * Abre una ventana modal para editar un evento seleccionado de la tabla.
     * Si un evento es seleccionado y el usuario confirma la edición, se abrirá una nueva ventana de edición.
     * Después de la edición, la tabla se actualizará con los nuevos datos.
     * Si no se selecciona ningún evento o si la tabla no es la correcta, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar un evento.
     */
    @FXML
    void editarEvento(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Eventos
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            Evento eventoSeleccionado = (Evento) tabla.getSelectionModel().getSelectedItem();

            if (eventoSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar el evento
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/evento.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    EventoController controller = loader.getController();

                    // Pasar el evento seleccionado al controlador para editar
                    controller.setEvento(eventoSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Evento");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un evento
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un evento para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un evento.");
            alert.showAndWait();
        }
    }

    /**
     * Abre una ventana modal para editar una olimpiada seleccionada de la tabla.
     * Si una olimpiada es seleccionada y el usuario confirma la edición, se abrirá una nueva ventana de edición.
     * Después de la edición, la tabla se actualizará con los nuevos datos.
     * Si no se selecciona ninguna olimpiada o si la tabla no es la correcta, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar una olimpiada.
     */
    @FXML
    void editarOlimpiada(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Olimpiadas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            Olimpiada olimpiadaSeleccionada = (Olimpiada) tabla.getSelectionModel().getSelectedItem();

            if (olimpiadaSeleccionada != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar la olimpiada
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/olimpiada.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    OlimpiadaController controller = loader.getController();

                    // Pasar la olimpiada seleccionada al controlador para editar
                    controller.setOlimpiada(olimpiadaSeleccionada);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Olimpiada");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una olimpiada
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione una olimpiada para editar.");
                alert.showAndWait();
            }
        } else {
            // Mostrar mensaje si la tabla seleccionada no es de Olimpiadas
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una olimpiada.");
            alert.showAndWait();
        }
    }

    /**
     * Abre una ventana modal para editar una participación seleccionada de la tabla.
     * Si una participación es seleccionada y el usuario confirma la edición, se abrirá una nueva ventana de edición.
     * Después de la edición, la tabla se actualizará con los nuevos datos.
     * Si no se selecciona ninguna participación o si la tabla no es la correcta, se muestra una advertencia.
     *
     * @param event El evento generado al hacer clic en el botón para editar una participación.
     */
    @FXML
    void editarParticipacion(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Participaciones
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Participaciones")) {
            Participacion participacionSeleccionada = (Participacion) tabla.getSelectionModel().getSelectedItem();

            if (participacionSeleccionada != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar la participación
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/participacion.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    ParticipacionController controller = loader.getController();

                    // Pasar la participación seleccionada al controlador para editar
                    controller.setParticipacion(participacionSeleccionada);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Participación");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una participación
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, selecciona una participación para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona una participación.");
            alert.showAndWait();
        }
    }



    /**
     * Filtra los elementos de la tabla basada en el texto ingresado en el campo de búsqueda.
     * Dependiendo de la tabla seleccionada en el comboBox, se filtran los datos de la lista correspondiente
     * (Deportistas, Deportes, Equipos, Olimpiadas, Eventos o Participaciones) utilizando el texto ingresado.
     * El filtro es sensible a mayúsculas y minúsculas.
     *
     * @param event El evento generado por la escritura en el campo de búsqueda.
     */
    @FXML
    void filtrar(KeyEvent event) {
        String textoBusqueda = tfNombre.getText() != null ? tfNombre.getText().toLowerCase() : ""; // Asegúrate de que el texto no sea null

        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            ObservableList<Deportista> todosDeportistas = DeportistaDAO.findAll();
            tabla.setItems(todosDeportistas.filtered(deportista ->
                    deportista.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            ObservableList<Deporte> todosDeportes = DeporteDAO.findAll();
            tabla.setItems(todosDeportes.filtered(deporte ->
                    deporte.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            ObservableList<Equipo> todosEquipos = EquipoDAO.findAll();
            tabla.setItems(todosEquipos.filtered(equipo ->
                    equipo.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            ObservableList<Olimpiada> todasOlimpiadas = OlimpiadaDAO.getAll();
            tabla.setItems(todasOlimpiadas.filtered(olimpiada ->
                    olimpiada.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            ObservableList<Evento> todosEventos = EventoDAO.getAll();
            tabla.setItems(todosEventos.filtered(evento ->
                    evento.getNombre().toLowerCase().contains(textoBusqueda)));

        } else {
            ObservableList<Participacion> todasParticipaciones = ParticipacionDAO.findAll();
            tabla.setItems(todasParticipaciones.filtered(participacion ->
                    participacion.getDeportista().getNombre().toLowerCase().contains(textoBusqueda)));
        }
        tabla.refresh();
    }



    /**
     * Cambia la vista de la tabla de acuerdo con la opción seleccionada en el comboBox.
     * Dependiendo de la opción seleccionada en `cbTablaElegida`, se invoca un metodo diferente
     * para generar la tabla correspondiente, ya sea para Deportistas, Deportes, Equipos,
     * Olimpiadas, Eventos o Participaciones.
     *
     * @param event El evento generado por la acción de cambiar la selección en el comboBox.
     */
    @FXML
    void cambiarDeTabla(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            generarDeportes();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            generarDeportistas();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            generarEquipos();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            generarEventos();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            generarOlimpiadas();
        } else {
            generarParticipaciones();
        }
    }


    /**
     * Genera la tabla para mostrar los deportes. Crea las columnas necesarias para mostrar
     * la información de los deportes y carga los datos desde la base de datos utilizando
     * el `DeporteDAO`. Limpia cualquier filtro o campo de búsqueda y refresca la tabla
     * con los datos de los deportes.
     */
    @FXML
    void generarDeportes() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Deporte
        TableColumn<Deporte, Integer> colIdDeporte = new TableColumn<>("ID Deporte");
        colIdDeporte.setCellValueFactory(new PropertyValueFactory<>("idDeporte"));

        TableColumn<Deporte, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdDeporte, colNombre);

        // Cargar los datos en la tabla
        ObservableList<Deporte> deportes = DeporteDAO.findAll();
        tabla.setItems(deportes);
    }


    /**
     * Genera la tabla para mostrar los deportistas. Crea las columnas necesarias para mostrar
     * la información de los deportistas, incluidos sus datos como ID, nombre, sexo, peso, altura
     * y foto. Además, configura un `cellFactory` para mostrar las fotos como imágenes en la tabla.
     *
     * Limpia cualquier filtro o campo de búsqueda y refresca la tabla con los datos de los deportistas
     * obtenidos desde el `DeportistaDAO`.
     */
    @FXML
    void generarDeportistas() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Deportista
        TableColumn<Deportista, Integer> colIdDeportista = new TableColumn<>("ID Deportista");
        colIdDeportista.setCellValueFactory(new PropertyValueFactory<>("idDeportista"));

        TableColumn<Deportista, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Deportista, String> colSexo = new TableColumn<>("Sexo");
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        TableColumn<Deportista, Integer> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<Deportista, Integer> colAltura = new TableColumn<>("Altura");
        colAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        TableColumn<Deportista, Blob> colFoto = new TableColumn<>("Foto");
        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));

        // Configurar el cell factory para mostrar las imágenes
        colFoto.setCellFactory(column -> new TableCell<Deportista, Blob>() {
            private final ImageView imageView = new ImageView();
            private final Image imagenPorDefecto = new Image(getClass().getResourceAsStream("/com/example/olimpiadas/img/iconitoOlimpiadas.png"));

            @Override
            protected void updateItem(Blob item, boolean empty) {
                super.updateItem(item, empty);

                // Asegúrate de limpiar el gráfico si la celda está vacía
                if (empty) {
                    setGraphic(null);
                } else {
                    if (item == null) {
                        // No hay imagen, usar imagen por defecto
                        imageView.setImage(imagenPorDefecto);
                    } else {
                        try {
                            // Convierte el Blob a un Image
                            Image image = new Image(item.getBinaryStream());
                            imageView.setImage(image);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            // En caso de error, establecer la imagen por defecto
                            imageView.setImage(imagenPorDefecto);
                        }
                    }

                    // Ajusta el tamaño de la imagen y mantiene su proporción
                    imageView.setFitWidth(50); // Establece el ancho de la imagen
                    imageView.setFitHeight(50); // Establece la altura de la imagen
                    imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen

                    // Establecer la gráfica de la celda
                    setGraphic(imageView); // Muestra la imagen en la celda
                }
            }
        });

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdDeportista, colNombre, colSexo, colPeso, colAltura, colFoto);

        // Cargar los datos en la tabla
        ObservableList<Deportista> deportistas = DeportistaDAO.findAll();
        tabla.setItems(deportistas);
    }


    /**
     * Genera la tabla para mostrar los equipos. Crea las columnas necesarias para mostrar
     * la información de los equipos, incluidos sus datos como ID, nombre e iniciales.
     *
     * Limpia cualquier filtro o campo de búsqueda y refresca la tabla con los datos de los equipos
     * obtenidos desde el `EquipoDAO`.
     */
    @FXML
    void generarEquipos() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Equipo
        TableColumn<Equipo, Integer> colIdEquipo = new TableColumn<>("ID Equipo");
        colIdEquipo.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));

        TableColumn<Equipo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Equipo, String> colIniciales = new TableColumn<>("Iniciales");
        colIniciales.setCellValueFactory(new PropertyValueFactory<>("iniciales"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdEquipo, colNombre, colIniciales);

        // Cargar los datos en la tabla
        ObservableList<Equipo> equipos = EquipoDAO.findAll();
        tabla.setItems(equipos);
    }


    /**
     * Genera la tabla para mostrar los eventos. Crea las columnas necesarias para mostrar
     * la información de los eventos, incluidos sus datos como ID, nombre, olimpiada y deporte.
     *
     * Limpia cualquier filtro o campo de búsqueda y refresca la tabla con los datos de los eventos
     * obtenidos desde el `EventoDAO`.
     */
    @FXML
    void generarEventos() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Evento
        TableColumn<Evento, Integer> colIdEvento = new TableColumn<>("ID Evento");
        colIdEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Evento, String> colOlimpiada = new TableColumn<>("Olimpiada");
        colOlimpiada.setCellValueFactory(cellData -> {
            Olimpiada olimpiada = cellData.getValue().getOlimpiada();
            return new SimpleStringProperty(olimpiada != null ? olimpiada.getNombre() : "");
        });

        TableColumn<Evento, String> colDeporte = new TableColumn<>("Deporte");
        colDeporte.setCellValueFactory(cellData -> {
            Deporte deporte = cellData.getValue().getDeporte();
            return new SimpleStringProperty(deporte != null ? deporte.getNombre() : "");
        });

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdEvento, colNombre, colOlimpiada, colDeporte);

        // Cargar los datos en la tabla
        ObservableList<Evento> eventos = EventoDAO.getAll();
        tabla.setItems(eventos);
    }


    /**
     * Genera la tabla para mostrar las olimpiadas. Crea las columnas necesarias para mostrar
     * la información de las olimpiadas, incluidos sus datos como ID, nombre, año, temporada
     * y ciudad.
     *
     * Limpia cualquier filtro o campo de búsqueda y refresca la tabla con los datos de las olimpiadas
     * obtenidos desde el `OlimpiadaDAO`.
     */
    @FXML
    void generarOlimpiadas()  {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Olimpiada
        TableColumn<Olimpiada, Integer> colIdOlimpiada = new TableColumn<>("ID Olimpiada");
        colIdOlimpiada.setCellValueFactory(new PropertyValueFactory<>("idOlimpiada"));

        TableColumn<Olimpiada, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Olimpiada, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));

        TableColumn<Olimpiada, String> colTemporada = new TableColumn<>("Temporada");
        colTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));

        TableColumn<Olimpiada, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdOlimpiada, colNombre, colAnio, colTemporada, colCiudad);

        // Cargar los datos en la tabla
        ObservableList<Olimpiada> olimpiadas = OlimpiadaDAO.getAll();

        tabla.setItems(olimpiadas);
    }


    /**
     * Genera la tabla para mostrar las participaciones. Crea las columnas necesarias para mostrar
     * la información de las participaciones, incluidos los datos como deportista, evento, equipo,
     * edad y medalla obtenida.
     *
     * Limpia cualquier filtro o campo de búsqueda y refresca la tabla con los datos de las participaciones
     * obtenidos desde el `ParticipacionDAO`.
     */
    @FXML
    void generarParticipaciones()  {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Participacion
        TableColumn<Participacion, String> colDeportista = new TableColumn<>("Deportista");
        colDeportista.setCellValueFactory(cellData -> {
            Deportista deportista = cellData.getValue().getDeportista();
            return new SimpleStringProperty(deportista != null ? deportista.getNombre() : "");
        });

        TableColumn<Participacion, String> colEvento = new TableColumn<>("Evento");
        colEvento.setCellValueFactory(cellData -> {
            Evento evento = cellData.getValue().getEvento();
            return new SimpleStringProperty(evento != null ? evento.getNombre() : "");
        });

        TableColumn<Participacion, String> colEquipo = new TableColumn<>("Equipo");
        colEquipo.setCellValueFactory(cellData -> {
            Equipo equipo = cellData.getValue().getEquipo();
            return new SimpleStringProperty(equipo != null ? equipo.getNombre() : "");
        });

        TableColumn<Participacion, Integer> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        TableColumn<Participacion, String> colMedalla = new TableColumn<>("Medalla");
        colMedalla.setCellValueFactory(new PropertyValueFactory<>("medalla"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colDeportista, colEvento, colEquipo, colEdad, colMedalla);

        // Cargar los datos en la tabla
        ObservableList<Participacion> participaciones = ParticipacionDAO.findAll();
        tabla.setItems(participaciones);
    }


}
