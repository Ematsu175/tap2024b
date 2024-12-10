package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SpotifyAdmin extends Stage {
    private Scene escena;
    private Label lblMensaje;
    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu Opciones;
    private MenuItem mitClientes, mitGenero, mitArtistas, mitAlbum, mitPrueba, mitCancion, mitAlbumCancion, mitInterprete, mitEstadisticas, mitcancionArtista;

    public SpotifyAdmin() {
        CrearUI();
        this.setTitle("Spotify");
        escena = new Scene(bdpPrincipal, 500, 500);
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
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
        Opciones.getItems().addAll(mitClientes, mitGenero, mitArtistas, mitAlbum, mitCancion, mitAlbumCancion, mitInterprete, mitEstadisticas, mitPrueba, mitcancionArtista);

        mnbPrincipal = new MenuBar(Opciones);
        mnbPrincipal.getStyleClass().add("menu-bar");
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(10);

        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/spotify.png").toString()));
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Label lblAdmin = new Label("Administrador");
        lblAdmin.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        vboxCenter.getChildren().addAll(imageView, lblAdmin);

        bdpPrincipal.setCenter(vboxCenter);
    }
}
