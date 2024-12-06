package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCell;
import com.example.tap2024b.components.ButtonCellAlbumCancion;
import com.example.tap2024b.models.AlbumCancionDAO;
import com.example.tap2024b.models.ArtistaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaAlbumCancion extends Stage {
    private TableView<AlbumCancionDAO> tbvAlbumCancion; // Cambiado a AlbumCancionDAO
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaAlbumCancion() {
        CrearUI();
        this.setTitle("Álbumes y Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitHeight(50);

        Button btnAddRelacion = new Button();
        btnAddRelacion.setOnAction(event -> new FormAlbumCancion(tbvAlbumCancion, null)); // Cambiado a FormAlbumCancion
        btnAddRelacion.setGraphic(imv);
        tlbMenu.getItems().add(btnAddRelacion);

        tbvAlbumCancion = new TableView<>();
        CrearTabla();

        vBox = new VBox(tlbMenu, tbvAlbumCancion);
        escena = new Scene(vBox, 600, 400);
    }

    private void CrearTabla() {
        TableColumn<AlbumCancionDAO, String> colAlbum = new TableColumn<>("Álbum");
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<AlbumCancionDAO, String> colCancion = new TableColumn<>("Canción");
        colCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<AlbumCancionDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellAlbumCancion("Editar"));

        TableColumn<AlbumCancionDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellAlbumCancion("Eliminar"));

        tbvAlbumCancion.getColumns().addAll(colAlbum, colCancion, tbcEditar, tbcEliminar);

        AlbumCancionDAO dao = new AlbumCancionDAO();
        tbvAlbumCancion.setItems(dao.selectAll());
    }
}
