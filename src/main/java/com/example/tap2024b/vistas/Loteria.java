package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Loteria extends Stage {
    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[] arrImg={"barril.jpeg","botella.jpeg","catrin.jpeg","chavorruco.jpeg","dorso.jpeg","luchador.jpeg","maceta.jpeg","rosa.jpeg","tacos.jpeg","venado.jpeg"};
    private Button[][] arrBtnTab;
    public Loteria(){
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        gdpTablilla= new GridPane();
        CrearTablilla();
        btnAnterior = new Button("Anterior");
        btnSiguiente = new Button("Siguiente");
        hBoxButtons = new HBox(btnAnterior, btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla, hBoxButtons);

        hBoxMain = new HBox(vbxTablilla);
        escena = new Scene(hBoxMain, 400,400);

    }

    private void CrearTablilla() {
        arrBtnTab=new Button[4][4];
        Image img;
        ImageView imv;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                img = new Image(getClass().getResource("/images/barril.jpeg").toString());
                imv = new ImageView(img);
                imv.setFitWidth(150);
                imv.setFitHeight(100);
                arrBtnTab[j][i] = new Button();
                arrBtnTab[j][i].setGraphic(imv);
                gdpTablilla.add(arrBtnTab[j][i],j,i);
            }
        }
    }
}
