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
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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
        File selectedFile = fileChooser.showOpenDialog(this);

        if (selectedFile != null) {
            // Mostrar la imagen en el ImageView
            Image image = new Image(selectedFile.toURI().toString());
            imvAlbum.setImage(image);

            // Guardar la ruta absoluta de la imagen en AlbumDAO
            objAlbum.setImg_album(image);
            objAlbum.setImagePath(selectedFile.getAbsolutePath());
        }
    }


    private void Guardar() {
        objAlbum.setAlbum(txtalbum.getText());
        objAlbum.setFecha_lanzamiento(txtFechaLanzamiento.getText());

        String mensaje;
        Alert.AlertType tipoAlerta;

        if (objAlbum.getId_album() > 0) {
            // Implementa la lógica de actualización aquí si es necesario
            mensaje = "Actualizar aún no está implementado.";
            tipoAlerta = Alert.AlertType.WARNING;
        } else {
            // Validar que se haya seleccionado una imagen antes de intentar guardar
            if (objAlbum.getImagePath() != null && !objAlbum.getImagePath().isEmpty()) {
                int rowsAffected = objAlbum.insert();
                if (rowsAffected > 0) {
                    mensaje = "Registro insertado correctamente.";
                    tipoAlerta = Alert.AlertType.INFORMATION;
                } else {
                    mensaje = "Error al insertar el registro. Revisa la ruta de la imagen.";
                    tipoAlerta = Alert.AlertType.ERROR;
                }
            } else {
                mensaje = "Por favor, selecciona una imagen antes de guardar.";
                tipoAlerta = Alert.AlertType.WARNING;
            }
        }

        // Mostrar mensaje de alerta
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        // Actualizar los datos del TableView
        tbvAlbum.setItems(objAlbum.selectAll());
        tbvAlbum.refresh();

        // Cerrar el formulario después de guardar
        this.close();
    }



}
