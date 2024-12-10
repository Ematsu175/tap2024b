package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
import com.example.tap2024b.components.ButtonCellGenero;
import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaGenero extends Stage {
    private TableView<GeneroDAO> tbvGenero;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public ListaGenero(){
        CrearUI();
        this.setTitle("Lista de generos");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/add.png").toString());
        imv.setFitHeight(100);
        imv.setFitHeight(100);
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormGenero(tbvGenero, null));
        tlbMenu.getItems().add(btnAddCliente);
        tlbMenu.getStyleClass().add("toolbar-background");
        btnAddCliente.setGraphic(imv);
        btnAddCliente.getStyleClass().add("add-button");

        tbvGenero = new TableView<>();

        CrearTable();
        tbvGenero.getStyleClass().add("custom-table");
        tbvGenero.getStyleClass().add("transparent-table");
        vBox = new VBox(tlbMenu,tbvGenero);
        vBox.getStyleClass().add("green-black-gradient");
        escena = new Scene(vBox,500,250);
    }
    private void CrearTable() {
        GeneroDAO objGenero = new GeneroDAO();

        // Columna para mostrar el nombre del género
        TableColumn<GeneroDAO, String> tbcNombre = new TableColumn<>("Genero");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("genero"));

        TableColumn<GeneroDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGenero("Editar");
            }
        });

        // Columna para el botón Eliminar
        TableColumn<GeneroDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<GeneroDAO, String>, TableCell<GeneroDAO, String>>() {
            @Override
            public TableCell<GeneroDAO, String> call(TableColumn<GeneroDAO, String> generoDAOStringTableColumn) {
                return new ButtonCellGenero("Eliminar");
            }
        });

        // Agregar columnas a la tabla
        tbvGenero.getColumns().addAll(tbcNombre, tbcEditar, tbcEliminar);

        // Configurar datos iniciales
        tbvGenero.setItems(objGenero.selectAll());
    }

}
