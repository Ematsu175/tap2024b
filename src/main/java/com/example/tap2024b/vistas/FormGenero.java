package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormGenero extends Stage {
    private Scene escena;
    private TextField txtGenero;
    private final GeneroDAO objGenero;
    private final TableView<GeneroDAO> tbvGenero;
    public FormGenero(TableView<GeneroDAO> tbv, GeneroDAO objg){
        tbvGenero = tbv;
        CrearUI();
        if (objg != null) {
            this.objGenero =  objg;
            txtGenero.setText(objg.getGenero());
            this.setTitle("Editar Genero");
        }else {
            this.objGenero = new GeneroDAO();
            this.setTitle("Insertar Genero");
        }
        this.setTitle("");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtGenero = new TextField();
        txtGenero.setPromptText("Nombre del genero");
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCliente());
        VBox vbox = new VBox(txtGenero, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 150);
    }

    private void GuardarCliente() {
        objGenero.setGenero(txtGenero.getText());
        String msj;
        Alert.AlertType type;

        if (objGenero.getId_genero()>0){
            objGenero.update();
        } else {
            if (objGenero.insert() > 0){
                msj= "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrio un error al insertar";
                type = Alert.AlertType.ERROR;
            }
            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();
        }

        tbvGenero.setItems(objGenero.selectAll());
        tbvGenero.refresh();
        this.close();
    }
}
