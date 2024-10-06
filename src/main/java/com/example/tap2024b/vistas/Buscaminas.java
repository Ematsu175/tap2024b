package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Buscaminas extends Stage {
    Scene escena;
    private VBox vboxPrincipal;
    private Label lblPrincipal;
    private TextField txtCantBombas;
    private Button btnCalcularBotones, btnBandera, btnQuitarBandera;
    private Button[][] arrBotones;
    private GridPane gdpBotones;
    int valor1, valor2;
    private char matriz[][];
    private char matrizMinas[][];
    private boolean modoBandera = false;

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
        btnCalcularBotones.setOnAction(event -> {
            if (txtCantBombas.getText().isEmpty()){
                lblPrincipal.setText("Igresa la cantidad de bombas que deseas");
            } else {
                if(Integer.parseInt(txtCantBombas.getText())<1 || Integer.parseInt(txtCantBombas.getText())>20){
                    lblPrincipal.setText("Debes ingresar un número entre 1 y 20");
                } else {
                    CrearTablero(txtCantBombas.getText());
                }
            }
        });
        btnBandera = new Button("Bandera");
        btnBandera.setOnAction(event -> {
            modoBandera = !modoBandera;
            if (modoBandera) {
                btnBandera.setText("Detener banderas");
            } else {
                btnBandera.setText("Colocar banderas");
            }
        });


        vboxPrincipal = new VBox(lblPrincipal, txtCantBombas, btnCalcularBotones,btnBandera);
        vboxPrincipal.setAlignment(Pos.TOP_CENTER);
        vboxPrincipal.setSpacing(10);
        escena = new Scene(vboxPrincipal, 800, 700);
    }

    private void CrearTablero(String v) {

        int cantMinas = Integer.parseInt(v);
        if (cantMinas<1 && cantMinas>20){

        }
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
        } else if (cantMinas>10 && cantMinas<=20){
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

                // Asignamos la acción de cada botón
                final int fila = i;
                final int columna = j;
                arrBotones[i][j].setOnAction(event -> {
                    if (modoBandera) {
                        // Si el botón ya tiene una imagen (graphic), la quitamos
                        if (arrBotones[fila][columna].getGraphic() != null) {
                            arrBotones[fila][columna].setGraphic(null); // Quitar la imagen
                        } else {
                            Image imagenBandera = new Image(getClass().getResource("/images/banderaB.png").toString());
                            ImageView imageView = new ImageView(imagenBandera);
                            imageView.setFitWidth(50);
                            imageView.setFitHeight(50);

                            arrBotones[fila][columna].setGraphic(imageView);
                        }
                    }
                });

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
