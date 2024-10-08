package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormCliente());
        tlbMenu.getItems().add(btnAddCliente);
        btnAddCliente.setGraphic(imv);

        tbvClientes = new TableView<>();

        CrearTable();
        vBox = new VBox(tlbMenu,tbvClientes);
        escena = new Scene(vBox,500,250);
    }

    private void CrearTable() {
        ClienteDAO objCliente = new ClienteDAO();

        TableColumn<ClienteDAO,String> tbcNombre = new TableColumn<>("Empleado");
        tbcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ClienteDAO,String> tbcTelefono = new TableColumn<>("Telefono");
        tbcTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<ClienteDAO,String> tbcEmail = new TableColumn<>("Email");
        tbcEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        tbvClientes.getColumns().addAll(tbcNombre, tbcTelefono, tbcEmail);
        tbvClientes.setItems(objCliente.selectAll());

    }


}
