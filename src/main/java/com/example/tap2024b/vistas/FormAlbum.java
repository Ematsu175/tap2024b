package com.example.tap2024b.vistas;

import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ArtistaDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FormAlbum extends Stage {
    private Scene escena;
    private TextField txtalbum, txtFechaLanzamiento;
    private AlbumDAO objAlbum;
    private ImageView imvAlbum;
    private final TableView<AlbumDAO> tbvAlbum;
    public FormAlbum(TableView<AlbumDAO> tbv, AlbumDAO objal){
        tbvAlbum = tbv;
        CrearUI();
        if (objal != null) {
            this.objAlbum =  objal;
            txtalbum.setText(objal.getAlbum());
            txtFechaLanzamiento.setText(objal.getFecha_lanzamiento());
            imvAlbum.setImage(objal.getImg_album());
            this.setTitle("Editar Artista");
        }else {
            this.objAlbum = new AlbumDAO();
            this.setTitle("Insertar Genero");
        }
        this.setTitle("");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtalbum = new TextField();
        txtalbum.setPromptText("Nombre del Album");
        txtFechaLanzamiento = new TextField();
        txtFechaLanzamiento.setPromptText("Fecha de lanzamiento");

        // Botón para seleccionar la imagen
        Button btnSelectImage = new Button("Seleccionar Imagen");
        btnSelectImage.setOnAction(event -> seleccionarImagen());

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> Guardar());

        imvAlbum = new ImageView();
        imvAlbum.setFitHeight(100);
        imvAlbum.setFitWidth(100);

        VBox vbox = new VBox(txtalbum, txtFechaLanzamiento, btnSelectImage, imvAlbum, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 250, 300);
    }

    private void seleccionarImagen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.jpg", "*.png", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imvAlbum.setImage(image);
            objAlbum.setImg_album(image);  // Asignar la imagen seleccionada al objeto
        }
    }

    private void Guardar() {
        objAlbum.setAlbum(txtalbum.getText());
        objAlbum.setFecha_lanzamiento(txtFechaLanzamiento.getText());
        objAlbum.setImg_album(imvAlbum.getImage());  // Obtener la imagen del ImageView

        String msj;
        Alert.AlertType type;

        if (objAlbum.getId_album() > 0) {
            objAlbum.update();
            msj = "Registro actualizado";
            type = Alert.AlertType.INFORMATION;
        } else {
            if (objAlbum.insert() > 0) {
                msj = "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrió un error al insertar";
                type = Alert.AlertType.ERROR;
            }
        }

        Alert alerta = new Alert(type);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

        //tbvAlbum.setItems(objAlbum.selectAll());  // Refrescar la tabla
        tbvAlbum.refresh();
        this.close();
    }

}
