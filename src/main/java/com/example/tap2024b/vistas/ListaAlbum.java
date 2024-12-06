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
        this.show();
    }

    private void CrearUI() {
        // Creación de la barra de herramientas
        tlbMenu = new ToolBar();

        // Icono del botón "Agregar Álbum"
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitWidth(50);

        // Botón para agregar un nuevo álbum
        Button btnAddAlbum = new Button();
        btnAddAlbum.setOnAction(event -> {
            new FormAlbum(tbvAlbum, null); // Abre el formulario para insertar un nuevo álbum
        });
        btnAddAlbum.setGraphic(imv);

        // Agregar botón a la barra de herramientas
        tlbMenu.getItems().add(btnAddAlbum);

        // Inicializar el TableView
        tbvAlbum = new TableView<>();
        CrearTable();

        // Configurar layout principal
        vBox = new VBox(tlbMenu, tbvAlbum);
        escena = new Scene(vBox, 800, 400);
    }

    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();

        // Columna para el nombre del álbum
        TableColumn<AlbumDAO, String> tbcAlbum = new TableColumn<>("Álbum");
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));

        // Columna para la fecha de lanzamiento
        TableColumn<AlbumDAO, String> tbcFechaLanzamiento = new TableColumn<>("Fecha de Lanzamiento");
        tbcFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fecha_lanzamiento"));

        // Columna para la imagen del álbum
        TableColumn<AlbumDAO, Image> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellFactory(param -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null); // Si no hay imagen, limpia la celda
                } else {
                    imageView.setImage(item); // Asigna la imagen al ImageView
                    imageView.setFitWidth(50); // Ajusta el ancho
                    imageView.setFitHeight(50); // Ajusta la altura
                    imageView.setPreserveRatio(true); // Mantén la proporción de la imagen
                    setGraphic(imageView); // Asigna el ImageView a la celda
                }
            }
        });
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("img_album"));

        // Columna para editar el álbum
        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellAlbum("Editar"));

        // Columna para eliminar el álbum
        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellAlbum("Eliminar"));

        // Agregar columnas al TableView
        tbvAlbum.getColumns().addAll(tbcAlbum, tbcFechaLanzamiento, tbcImagen, tbcEditar, tbcEliminar);

        // Cargar datos desde la base de datos
        CargarDatos();
    }


    private void CargarDatos() {
        AlbumDAO objAlbum = new AlbumDAO();
        tbvAlbum.setItems(objAlbum.selectAll());
    }


}

