package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormArtista extends Stage {
    private Scene escena;
    private TextField txtArtista;
    private ArtistaDAO objArtista;
    private final TableView<ArtistaDAO> tbvArtista;
    public FormArtista(TableView<ArtistaDAO> tbv, ArtistaDAO obja){
        tbvArtista = tbv;
        CrearUI();
        if (obja != null) {
            this.objArtista =  obja;
            txtArtista.setText(obja.getArtista());
            this.setTitle("Editar Artista");
        }else {
            this.objArtista = new ArtistaDAO();
            this.setTitle("Insertar Genero");
        }
        this.setTitle("");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtArtista = new TextField();
        txtArtista.setPromptText("Nombre del Artista");
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCliente());
        VBox vbox = new VBox(txtArtista, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 150);
    }

    private void GuardarCliente() {
        objArtista.setArtista(txtArtista.getText());
        String msj;
        Alert.AlertType type;

        if (objArtista.getId_artista()>0){
            objArtista.update();
        } else {
            if (objArtista.insert() > 0){
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

        tbvArtista.setItems(objArtista.selectAll());
        tbvArtista.refresh();
        this.close();
    }
}
