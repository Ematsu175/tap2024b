package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaClientes extends Stage {
    private TableView<ClienteDAO> tbvClientes;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    public ListaClientes(){
        CrearUI();
        this.setTitle("Lista de clientes");
        this.setScene(escena);
        escena.getStylesheets().add(getClass().getResource("/styles/spotify.css").toExternalForm());
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/add.png").toString());
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormCliente(tbvClientes, null));
        tlbMenu.getItems().add(btnAddCliente);
        tlbMenu.getStyleClass().add("toolbar-background");
        btnAddCliente.setGraphic(imv);
        btnAddCliente.getStyleClass().add("add-button");

        tbvClientes = new TableView<>();

        CrearTable();

        tbvClientes.getStyleClass().add("custom-table");
        tbvClientes.getStyleClass().add("transparent-table");
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox,500,500);
    }

    private void CrearTable() {
        ClienteDAO objCliente = new ClienteDAO();

        TableColumn<ClienteDAO,String> tbcNombre = new TableColumn<>("Cliente");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ClienteDAO,String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ClienteDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ClienteDAO,String> tbcEditar = new TableColumn<>("");
        tbcEditar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Editar");
            }
        });

        TableColumn<ClienteDAO,String> tbcEliminar = new TableColumn<>("");
        tbcEliminar.setCellFactory(new Callback<TableColumn<ClienteDAO, String>, TableCell<ClienteDAO, String>>() {
            @Override
            public TableCell<ClienteDAO, String> call(TableColumn<ClienteDAO, String> clienteDAOStringTableColumn) {
                return new ButtonCell("Eliminar");
            }
        });

        tbvClientes.getColumns().addAll(tbcNombre, tbcTelefono, tbcEmail, tbcEditar, tbcEliminar);
        tbvClientes.setItems(objCliente.selectAll());

    }


}
