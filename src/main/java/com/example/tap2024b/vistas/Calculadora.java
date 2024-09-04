package com.example.tap2024b.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {

    private Button[][] arrBtns;
    private TextField txtPantalla;
    private GridPane gdpTeclado;
    private VBox vbox;
    private Scene escena;
    private String[] strTeclas = {"7","8","9","*","4","5","6","/","1","2","3","+","0",".","=","-"};
    private Button btnClear;
    private void CrearUI(){
        arrBtns =  new Button[4][4];
        txtPantalla = new TextField("0");
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();
        btnClear = new Button("CE");
        btnClear.setId("font-button");
        vbox = new VBox(txtPantalla, gdpTeclado, btnClear);
        escena = new Scene(vbox,300, 300);
    }

    private void CrearTeclado(){
        //columna i
        for (int i = 0; i < arrBtns.length; i++) {
            //renglon j
            for (int j = 0; j < arrBtns.length; j++) {
                arrBtns[j][i] = new Button(strTeclas[4*i+j]);
                arrBtns[j][i].setPrefSize(75,75);
                int finalI = i;
                int finalJ = j;
                arrBtns[j][i].setOnAction(event -> DetectarTecla(strTeclas[4* finalI + finalJ]));
                gdpTeclado.add(arrBtns[j][i],j,i);
            }
        }
    }

    public Calculadora(){
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/calculadora.css").toExternalForm());
        this.show();
    }

    private void DetectarTecla(String tecla) {

        txtPantalla.appendText(tecla);
        String numero1="", numero2="";
        String operador="";
        if (txtPantalla.getText()!="+" || txtPantalla.getText()!="-" || txtPantalla.getText()!="*" ||
                txtPantalla.getText()!="/" || txtPantalla.getText()!="."){
            numero1=txtPantalla.getText();
            System.out.println("El numero es: "+numero1);
        } else {
            operador = txtPantalla.getText();
            System.out.println("el operador es: "+operador);
        }

    }
}
