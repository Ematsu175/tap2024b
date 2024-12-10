package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellArtista;
import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaArtista extends Stage {
    private TableView<ArtistaDAO> tbvArtista;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaArtista(){
        CrearUI();
        this.setTitle("Lista de Artistas");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/add.png").toString());
        imv.setFitHeight(50);
        imv.setFitHeight(50);
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormArtista(tbvArtista, null));
        tlbMenu.getItems().add(btnAddCliente);
        tlbMenu.getStyleClass().add("toolbar-background");
        btnAddCliente.setGraphic(imv);
        btnAddCliente.getStyleClass().add("add-button");

        tbvArtista = new TableView<>();

        CrearTable();
        tbvArtista.getStyleClass().add("custom-table");
        tbvArtista.getStyleClass().add("transparent-table");
        vBox = new VBox(tlbMenu,tbvArtista);
        escena = new Scene(vBox,500,250);
    }
    private void CrearTable() {
        ArtistaDAO objArtista = new ArtistaDAO();

        TableColumn<ArtistaDAO, String> tbcArtista = new TableColumn<>("Artista");
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));

        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> artistaDAOStringTableColumn) {
                return new ButtonCellArtista("Editar");
            }
        });

        // Columna para el botón Eliminar
        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ArtistaDAO, String>, TableCell<ArtistaDAO, String>>() {
            @Override
            public TableCell<ArtistaDAO, String> call(TableColumn<ArtistaDAO, String> artistaDAOStringTableColumn) {
                return new ButtonCellArtista("Eliminar");
            }
        });

        // Agregar columnas al TableView
        tbvArtista.getColumns().addAll(tbcArtista, tbcEditar, tbcEliminar);

        // Configurar los datos iniciales
        tbvArtista.setItems(objArtista.selectAll());
    }
}
