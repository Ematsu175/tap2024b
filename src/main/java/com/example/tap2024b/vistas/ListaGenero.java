package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
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
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(100);
        imv.setFitHeight(100);
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormGenero(tbvGenero, null));
        tlbMenu.getItems().add(btnAddCliente);
        btnAddCliente.setGraphic(imv);

        tbvGenero = new TableView<>();

        CrearTable();
        vBox = new VBox(tlbMenu,tbvGenero);
        escena = new Scene(vBox,500,250);
    }
    private void CrearTable() {
        GeneroDAO objGenero = new GeneroDAO();

        // Columna para mostrar el nombre del género
        TableColumn<GeneroDAO, String> tbcNombre = new TableColumn<>("Genero");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("genero"));

        TableColumn<GeneroDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Editar");
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> {
                        GeneroDAO genero = getTableView().getItems().get(getIndex());
                        new FormGenero(tbvGenero, genero);
                    });
                    setGraphic(btn);
                }
            }
        });

        // Columna para el botón Eliminar
        TableColumn<GeneroDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Eliminar");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setOnAction(event -> {
                        GeneroDAO genero = getTableView().getItems().get(getIndex());
                        genero.delete();
                        tbvGenero.setItems(objGenero.selectAll()); // Refresca la tabla
                    });
                    setGraphic(btn);
                }
            }
        });

        // Agregar columnas a la tabla
        tbvGenero.getColumns().addAll(tbcNombre, tbcEditar, tbcEliminar);

        // Configurar datos iniciales
        tbvGenero.setItems(objGenero.selectAll());
    }

}
