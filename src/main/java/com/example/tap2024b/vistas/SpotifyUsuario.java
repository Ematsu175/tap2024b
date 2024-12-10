package com.example.tap2024b.vistas;

import com.example.tap2024b.models.CancionDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SpotifyUsuario extends Stage {
    private Scene escena;
    private Label lblMensaje;
    private BorderPane bdpPrincipal;
    private MenuBar mnbPrincipal;
    private Menu Opciones;
    private MenuItem mitClientes, mitGenero, mitArtistas, mitAlbum, mitPrueba, mitCancion, mitAlbumCancion, mitInterprete, mitVenta;


    public SpotifyUsuario(){
        CrearUI();
        this.setTitle("Spotify Usuario");
        escena=new Scene(bdpPrincipal,500,500);
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.show();
    }

    private void CrearUI() {

        mitVenta = new MenuItem("Venta");
        mitVenta.setOnAction(event -> new ListaVenta());

        Opciones = new Menu("Opciones");
        Opciones.getItems().addAll( mitVenta);

        mnbPrincipal = new MenuBar(Opciones);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

        lblMensaje = new Label("Bienvenido!");

        VBox vboxCenter = new VBox();
        vboxCenter.setAlignment(Pos.CENTER);
        vboxCenter.setSpacing(10);

        ImageView imageView = new ImageView(new Image(getClass().getResource("/images/spotify.png").toString())); // Cambia la ruta por la imagen correcta
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);

        Label lblAdmin = new Label("Usuario");
        lblAdmin.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        vboxCenter.getChildren().addAll(imageView, lblAdmin);

        bdpPrincipal.setCenter(vboxCenter);

    }
}
