package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SpotifyAdmin extends Stage {
    private Scene escena;
    private Label lblMensaje;


    public SpotifyAdmin(){
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblMensaje = new Label("Bienvenido Admin!");
        escena=new Scene(lblMensaje,500,500);
    }
}
