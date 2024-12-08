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
    private MenuItem mitClientes, mitGenero, mitArtistas, mitAlbum, mitPrueba, mitCancion, mitAlbumCancion, mitInterprete, mitEstadisticas, mitcancionArtista;


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

        mitAlbum = new MenuItem("Albúm");
        mitAlbum.setOnAction(event -> new ListaAlbum());

        mitPrueba = new MenuItem("Prueba");
        mitPrueba.setOnAction(event -> new prueba());

        mitCancion = new MenuItem("Cancion");
        mitCancion.setOnAction(event -> new ListaCanciones());

        mitAlbumCancion = new MenuItem("Album y Cancion");
        mitAlbumCancion.setOnAction(event -> new ListaAlbumCancion());

        mitInterprete = new MenuItem("Interpretacion");
        mitInterprete.setOnAction(event -> new ListaInterprete());

        mitEstadisticas = new MenuItem("Estadísticas");
        mitEstadisticas.setOnAction(event -> new Estadisticas());

        mitcancionArtista = new MenuItem("Pdf Artistas Canciones");
        mitcancionArtista.setOnAction(event -> new InformeArtistasCanciones());

        Opciones = new Menu("Opciones");
        Opciones.getItems().addAll(mitClientes, mitGenero, mitArtistas, mitAlbum, mitCancion, mitAlbumCancion, mitInterprete, mitEstadisticas,mitPrueba, mitcancionArtista);

        mnbPrincipal = new MenuBar(Opciones);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

        lblMensaje = new Label("Bienvenido Admin!");

    }
}
