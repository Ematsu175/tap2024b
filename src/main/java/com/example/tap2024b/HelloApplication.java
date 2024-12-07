package com.example.tap2024b;

import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.vistas.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menSalir;
    private MenuItem mitCalculadora, mitLoteria, mitSpotify, mitBuscaminas, mitImpresora;


    public void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event -> new Calculadora());

        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction(event -> new Loteria());

        mitSpotify = new MenuItem("Spotify");
        mitSpotify.setOnAction(event -> new Spotify());

        mitBuscaminas = new MenuItem("Buscaminas");
        mitBuscaminas.setOnAction(event -> new Buscaminas());

        mitImpresora = new MenuItem("Impresora");
        mitImpresora.setOnAction(event -> new impresion());


        menCompetencia1 = new Menu("Competencia1");
        menCompetencia1.getItems().addAll(mitCalculadora, mitLoteria, mitSpotify, mitBuscaminas);

        menCompetencia2 = new Menu("Competencia2");
        menCompetencia2.getItems().addAll(mitImpresora);

        mnbPrincipal = new MenuBar(menCompetencia1, menCompetencia2);
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

