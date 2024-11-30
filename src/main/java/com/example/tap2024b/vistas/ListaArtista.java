package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitHeight(50);
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormArtista(tbvArtista, null));
        tlbMenu.getItems().add(btnAddCliente);
        btnAddCliente.setGraphic(imv);

        tbvArtista = new TableView<>();

        CrearTable();
        vBox = new VBox(tlbMenu,tbvArtista);
        escena = new Scene(vBox,500,250);
    }
    private void CrearTable() {
        ArtistaDAO objArtista = new ArtistaDAO();

        TableColumn<ArtistaDAO, String> tbcArtista = new TableColumn<>("Artista");
        tbcArtista.setCellValueFactory(new PropertyValueFactory<>("artista"));

        TableColumn<ArtistaDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Editar");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> {
                        ArtistaDAO artista = getTableView().getItems().get(getIndex());
                        new FormArtista(tbvArtista, artista);
                    });
                    setGraphic(btn);
                }
            }
        });

        // Columna para el botón Eliminar
        TableColumn<ArtistaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Eliminar");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> {
                        ArtistaDAO artista = getTableView().getItems().get(getIndex());
                        artista.delete();
                        tbvArtista.setItems(objArtista.selectAll()); // Refresca la tabla
                    });
                    setGraphic(btn);
                }
            }
        });

        // Agregar columnas al TableView
        tbvArtista.getColumns().addAll(tbcArtista, tbcEditar, tbcEliminar);

        // Configurar los datos iniciales
        tbvArtista.setItems(objArtista.selectAll());
    }
}
