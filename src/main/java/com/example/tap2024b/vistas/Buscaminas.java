package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Buscaminas extends Stage {
    Scene escena;
    private VBox vboxPrincipal;
    private Label lblPrincipal;
    private TextField txtCantBombas;
    private Button btnCalcularBotones;
    private Button[][] arrBotones;
    private GridPane gdpBotones;
    int valor1, valor2;
    private char matriz[][];
    private char matrizMinas[][];

    public Buscaminas() {
        CrearUI();
        this.setTitle("Buscaminas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        lblPrincipal = new Label("Ingresa un número de bombas");
        txtCantBombas = new TextField();
        txtCantBombas.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtCantBombas.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        btnCalcularBotones = new Button("Calcular");
        btnCalcularBotones.setOnAction(event -> CrearTablero(txtCantBombas.getText()));

        vboxPrincipal = new VBox(lblPrincipal, txtCantBombas, btnCalcularBotones);
        vboxPrincipal.setAlignment(Pos.TOP_CENTER);
        vboxPrincipal.setSpacing(10);
        escena = new Scene(vboxPrincipal, 800, 700);
    }

    private void CrearTablero(String v) {

        int cantMinas = Integer.parseInt(v);
        if (cantMinas<=5){
            valor1 = 4;
            valor2 = 4;
            matriz= new char[valor1][valor2];
            generarTablero(matriz);
            matrizMinas = new char[valor1][valor2];
            generarTablero(matrizMinas);
            generarMinas(valor1,valor2,matrizMinas,cantMinas);
        } else if (cantMinas>5 && cantMinas<=10){
            valor1 = 5;
            valor2 = 5;
            matriz= new char[valor1][valor2];
            generarTablero(matriz);
            matrizMinas = new char[valor1][valor2];
            generarTablero(matrizMinas);
            generarMinas(valor1,valor2,matrizMinas,cantMinas);
        } else if (cantMinas>10 && cantMinas<20){
            valor1 = 6;
            valor2 = 6;
            matriz= new char[valor1][valor2];
            generarTablero(matriz);
            matrizMinas = new char[valor1][valor2];
            generarTablero(matrizMinas);
            generarMinas(valor1,valor2,matrizMinas,cantMinas);
        }

        // Eliminar cualquier GridPane anterior si ya se había creado un tablero
        if (gdpBotones != null) {
            vboxPrincipal.getChildren().remove(gdpBotones);
        }

        // Inicializar el GridPane
        gdpBotones = new GridPane();
        gdpBotones.setAlignment(Pos.CENTER);

        // Inicializar el array después de conocer los valores de la matriz
        arrBotones = new Button[valor1][valor2];
        for (int i = 0; i < matrizMinas.length; i++) {
            for (int j = 0; j < matrizMinas.length; j++) {
                System.out.println(String.valueOf(matrizMinas[i][j]));
            }

        }
        for (int i = 0; i < valor1; i++) {
            for (int j = 0; j < valor2; j++) {
                arrBotones[i][j] = new Button();
                arrBotones[i][j].setPrefSize(75, 75);

                gdpBotones.add(arrBotones[i][j], j, i);
                arrBotones[i][j].setText(String.valueOf(matrizMinas[i][j]));
            }
        }

        System.out.println("max lenght: "+ matriz.length);

        VBox.setVgrow(gdpBotones, Priority.ALWAYS);
        vboxPrincipal.getChildren().add(gdpBotones);

    }
    private void generarTablero(char [][] matriz){
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz.length; j++) {
                matriz[i][j]='-';
            }
        }

    }

    private void generarMinas(int randomx, int randomy, char [][] matriz, int numeroMinas){
        boolean unaVez;
        do{
            for (int i = 0; i < matriz.length && numeroMinas>0; i++) {
                unaVez = false;
                for (int j = 0; j < matriz.length && numeroMinas>0; j++) {
                    if (unaVez==false){
                        matriz[i][(int)(Math.random() * matriz[0].length)] = '@';
                        numeroMinas--;
                    }
                    unaVez=true;

                }
            }
        } while (numeroMinas >0);

    }

}
