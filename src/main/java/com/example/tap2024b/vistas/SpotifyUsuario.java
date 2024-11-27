package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SpotifyUsuario extends Stage {
    private Scene escena;
    private Label lblMensaje;


    public SpotifyUsuario(){
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblMensaje = new Label("Bienvenido Usuario!");
        escena=new Scene(lblMensaje,500,500);
    }
}
