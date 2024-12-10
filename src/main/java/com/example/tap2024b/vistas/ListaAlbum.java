package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellAlbum;
import com.example.tap2024b.components.ButtonCellArtista;
import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ArtistaDAO;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.InputStream;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tbvAlbum;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;


    public ListaAlbum() {
        CrearUI();

        tbvAlbum.refresh();
        this.setTitle("Albums");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();

        ImageView imv = new ImageView(getClass().getResource("/images/add.png").toString());
        imv.setFitHeight(50);
        imv.setFitWidth(50);

        Button btnAddAlbum = new Button();
        btnAddAlbum.setOnAction(event -> {
            new FormAlbum(tbvAlbum, null);
        });
        btnAddAlbum.setGraphic(imv);
        btnAddAlbum.getStyleClass().add("add-button");

        tlbMenu.getItems().add(btnAddAlbum);
        tlbMenu.getStyleClass().add("toolbar-background");


        tbvAlbum = new TableView<>();

        CrearTable();

        // Aplica los estilos
        tbvAlbum.getStyleClass().add("custom-table");
        tbvAlbum.getStyleClass().add("transparent-table");


        vBox = new VBox(tlbMenu, tbvAlbum);
        vBox.getStyleClass().add("green-black-gradient");

        escena = new Scene(vBox, 800, 400);
    }


    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();

        TableColumn<AlbumDAO, String> tbcAlbum = new TableColumn<>("Álbum");
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<AlbumDAO, String> tbcFechaLanzamiento = new TableColumn<>("Fecha de Lanzamiento");
        tbcFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fecha_lanzamiento"));

        TableColumn<AlbumDAO, Image> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(item);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    imageView.setPreserveRatio(true);
                    setGraphic(imageView);
                }
            }
        });
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("img_album"));

        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellAlbum("Editar"));

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellAlbum("Eliminar"));

        tbvAlbum.getColumns().addAll(tbcAlbum, tbcFechaLanzamiento, tbcImagen, tbcEditar, tbcEliminar);

        CargarDatos();
    }


    private void CargarDatos() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum.setItems(objAlbum.selectAll());
    }


}

