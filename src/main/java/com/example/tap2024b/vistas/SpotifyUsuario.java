package com.example.tap2024b.vistas;

import com.example.tap2024b.models.CancionDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
        this.show();
    }

    private void CrearUI() {

        mitCancion = new MenuItem("Cancion");
        mitCancion.setOnAction(event -> new ListaCanciones());

        mitVenta = new MenuItem("Venta");
        mitVenta.setOnAction(event -> new ListaVenta());

        Opciones = new Menu("Opciones");
        Opciones.getItems().addAll( mitCancion, mitVenta);

        mnbPrincipal = new MenuBar(Opciones);
        bdpPrincipal = new BorderPane();
        bdpPrincipal.setTop(mnbPrincipal);

        lblMensaje = new Label("Bienvenido Admin!");

    }
}
