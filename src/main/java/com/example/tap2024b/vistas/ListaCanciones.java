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
            new FormCancion(tbvCancion, null);
        });
        btnAddAlbum.setGraphic(imv);
        btnAddAlbum.getStyleClass().add("add-button");

        tlbMenu.getItems().add(btnAddAlbum);
        tlbMenu.getStyleClass().add("toolbar-background");

        tbvCancion = new TableView<>();
        CrearTable();
        tbvCancion.getStyleClass().add("custom-table");
        tbvCancion.getStyleClass().add("transparent-table");
        vBox = new VBox(tlbMenu, tbvCancion);
        escena = new Scene(vBox, 800, 400);
    }

    private void CrearTable() {
        CancionDAO objCan = new CancionDAO();

        TableColumn<CancionDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<CancionDAO, String> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<CancionDAO, Float> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<CancionDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        TableColumn<CancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellCancion("Editar"));

        TableColumn<CancionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellCancion("Eliminar"));

        tbvCancion.getColumns().addAll(tbcCancion, tbcDuracion, tbcCosto, tbcGenero, tbcEditar, tbcEliminar);

        tbvCancion.setItems(objCan.selectAll());
    }


}
