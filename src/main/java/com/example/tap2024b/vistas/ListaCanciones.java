package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellAlbum;
import com.example.tap2024b.components.ButtonCellCancion;
import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.CancionDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaCanciones extends Stage {
    private TableView<CancionDAO> tbvCancion;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;


    public ListaCanciones() {
        CrearUI();

        tbvCancion.refresh();
        this.setTitle("Albums");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitWidth(50);
        Button btnAddAlbum = new Button();
        btnAddAlbum.setOnAction(event -> {
            new FormCancion(tbvCancion, null);
        });
        btnAddAlbum.setGraphic(imv);

        // Agregar botón a la barra de herramientas
        tlbMenu.getItems().add(btnAddAlbum);

        // Inicializar el TableView
        tbvCancion = new TableView<>();
        CrearTable();

        // Configurar layout principal
        vBox = new VBox(tlbMenu, tbvCancion);
        escena = new Scene(vBox, 800, 400);
    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();

        // Columna para el nombre de la canción
        TableColumn<CancionDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        // Columna para la duración
        TableColumn<CancionDAO, String> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Columna para el costo
        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        // Columna para el nombre del género
        TableColumn<CancionDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        // Columna para editar
        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellCancion("Editar"));

        // Columna para eliminar
        TableColumn<CancionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellCancion("Eliminar"));

        // Agregar columnas al TableView
        tbvCancion.getColumns().addAll(tbcCancion, tbcDuracion, tbcCosto, tbcGenero, tbcEditar, tbcEliminar);

        // Configurar los datos iniciales
        tbvCancion.setItems(objCan.selectAll());
    }


}
