package com.example.tap2024b;

import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.vistas.Calculadora;
import com.example.tap2024b.vistas.Loteria;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menSalir;
    private MenuItem mitCalculadora, mitLoteria;


    public void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());

        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());

        menCompetencia1 = new Menu("Competencia1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria);
        //menCompetencia1.getItems().addAll(mitLoteria);

        mnbPrincipal = new MenuBar(menCompetencia1);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);
    }
    @Override
    public void start(Stage stage) throws IOException {
        CrearUI();
        Scene scene = new Scene(bdpPrincipal, 320, 240);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setTitle("TAP2024B");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();

        Conexion.CrearConexion();
    }

    public static void main(String[] args) {
        launch();
    }
}

// 16 botones
// 1 textfield
// 1 girdpane
// 1 vbox


// git pull origin main --> subir a repositorio colaborativo
