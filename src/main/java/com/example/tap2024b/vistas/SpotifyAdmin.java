package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SpotifyAdmin extends Stage {
    private Scene escena;
    private Label lblMensaje;
    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu Opciones;
    private MenuItem mitClientes, mitGenero, mitArtistas;


    public SpotifyAdmin(){
        CrearUI();
        this.setTitle("Spotify");
        escena=new Scene(bdpPrincipal,500,500);
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        mitClientes = new MenuItem("Clientes");
        mitClientes.setOnAction(event -> new ListaClientes());

        mitGenero = new MenuItem("Genero");
        mitGenero.setOnAction(event -> new ListaGenero());

        mitArtistas = new MenuItem("Artistas");
        mitArtistas.setOnAction(event -> new ListaArtista());

        Opciones = new Menu("Opciones");
        Opciones.getItems().addAll(mitClientes, mitGenero, mitArtistas);

        mnbPrincipal = new MenuBar(Opciones);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

        lblMensaje = new Label("Bienvenido Admin!");

    }
}
