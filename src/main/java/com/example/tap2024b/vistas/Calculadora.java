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
    String numero="", operador="";
    double n1=0,n2=0, total=0;

    private void CrearUI(){
        arrBtns =  new Button[4][4];
        txtPantalla = new TextField();
        txtPantalla.setAlignment(Pos.CENTER_RIGHT);
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        CrearTeclado();
        btnClear = new Button("CE");
        btnClear.setPrefSize(75,75);
        btnClear.setId("color-btnclear");
        vbox = new VBox(txtPantalla, gdpTeclado, btnClear);
        escena = new Scene(vbox,300, 375);
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

                if (arrBtns[j][i].getText().equals("+") || arrBtns[j][i].getText().equals("-") || arrBtns[j][i].getText().equals("*")
                        || arrBtns[j][i].getText().equals("/") || arrBtns[j][i].getText().equals("=")) {
                    arrBtns[j][i].setId("color-operador");
                } else {
                    arrBtns[j][i].setId("color-numeros");
                }

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

        switch (tecla) {
            case "9":
                numero += "9";
                txtPantalla.appendText("9");
                break;
            case "8":
                numero += "8";
                txtPantalla.appendText("8");
                break;
            case "7":
                numero += "7";
                txtPantalla.appendText("7");
                break;
            case "6":
                numero += "6";
                txtPantalla.appendText("6");
                break;
            case "5":
                numero += "5";
                txtPantalla.appendText("5");
                break;
            case "4":
                numero += "4";
                txtPantalla.appendText("4");
                break;
            case "3":
                numero += "3";
                txtPantalla.appendText("3");
                break;
            case "2":
                numero += "2";
                txtPantalla.appendText("2");
                break;
            case "1":
                numero += "1";
                txtPantalla.appendText("1");
                break;
            case "0":
                numero += "0";
                txtPantalla.appendText("0");
                break;
            case ".":
                // Validar si es el primer caracter
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("NO SE PUEDE EMPEZAR CON UN PUNTO");
                    DesactivarBotones();
                } else {
                    numero += ".";
                    txtPantalla.appendText(".");
                }
                break;

            //Operadores
            case "+":
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("ERROR");
                    DesactivarBotones();
                } else {
                    n1=Double.parseDouble(txtPantalla.getText());
                    operador="+";
                    txtPantalla.clear();

                    arrBtns[3][0].setDisable(true);
                    arrBtns[3][1].setDisable(true);
                    arrBtns[3][2].setDisable(true);
                    arrBtns[3][3].setDisable(true);
                }
                break;
            case "-":
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("ERROR");
                    DesactivarBotones();
                } else {
                    n1=Double.parseDouble(txtPantalla.getText());
                    operador="-";
                    txtPantalla.clear();

                    arrBtns[3][0].setDisable(true);
                    arrBtns[3][1].setDisable(true);
                    arrBtns[3][2].setDisable(true);
                    arrBtns[3][3].setDisable(true);
                }
                break;
            case "*":
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("ERROR");
                    DesactivarBotones();
                } else {
                    n1=Double.parseDouble(txtPantalla.getText());
                    operador="*";
                    txtPantalla.clear();

                    arrBtns[3][0].setDisable(true);
                    arrBtns[3][1].setDisable(true);
                    arrBtns[3][2].setDisable(true);
                    arrBtns[3][3].setDisable(true);
                }
                break;
            case "/":
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("ERROR");
                    DesactivarBotones();
                } else {
                    n1=Double.parseDouble(txtPantalla.getText());
                    operador="/";
                    txtPantalla.clear();

                    arrBtns[3][0].setDisable(true);
                    arrBtns[3][1].setDisable(true);
                    arrBtns[3][2].setDisable(true);
                    arrBtns[3][3].setDisable(true);
                }
                break;

            case "=":
                if (txtPantalla.getText().isEmpty()) {
                    // No permitir agregar un punto como primer carácter
                    txtPantalla.appendText("ERROR COLOCA NUMEROS");
                    DesactivarBotones();
                } else {
                    if (operador.equals("+")){
                        n2=Double.parseDouble(txtPantalla.getText());
                        txtPantalla.clear();
                        total = n1+n2;
                        txtPantalla.setText(String.valueOf(total));
                        DesactivarBotones();
                    } else if (operador.equals("-")) {
                        n2=Double.parseDouble(txtPantalla.getText());
                        txtPantalla.clear();
                        total = n1-n2;
                        txtPantalla.setText(String.valueOf(total));
                        DesactivarBotones();
                    } else if (operador.equals("*")) {
                        n2=Double.parseDouble(txtPantalla.getText());
                        txtPantalla.clear();
                        total = n1*n2;
                        txtPantalla.setText(String.valueOf(total));
                        DesactivarBotones();
                    } else if (operador.equals("/")) {
                        n2=Double.parseDouble(txtPantalla.getText());
                        if (n2 == 0) {
                            txtPantalla.setText("NO SE PUEDE DIVIDIR ENTRE 0");
                            DesactivarBotones();
                            break;
                        } else {
                            txtPantalla.clear();
                            total = n1/n2;
                            txtPantalla.setText(String.valueOf(total));
                            DesactivarBotones();
                        }

                    }

                }
                break;

        }
        btnClear.setOnAction(event -> {
            numero="";
            n1=0;
            n2=0;
            total=0;
            txtPantalla.clear();
            ActivarBotones();
        });
        System.out.println("Numero 1: "+n1);
        System.out.println("Operador: "+operador);
        System.out.println("Numero 2: "+n2);
        System.out.println("El total es: "+total);

    }

    private void DesactivarBotones() {
        for (int i = 0; i < arrBtns.length; i++) {
            for (int j = 0; j < arrBtns[i].length; j++) {
                arrBtns[j][i].setDisable(true);
            }
        }
    }
    private void ActivarBotones() {
        for (int i = 0; i < arrBtns.length; i++) {
            for (int j = 0; j < arrBtns[i].length; j++) {
                arrBtns[j][i].setDisable(false);
            }
        }
    }



}
