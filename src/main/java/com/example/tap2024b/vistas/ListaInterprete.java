package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellAlbumCancion;
import com.example.tap2024b.components.ButtonCellInterprete;
import com.example.tap2024b.models.AlbumCancionDAO;
import com.example.tap2024b.models.InterpreteDao;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListaInterprete extends Stage {
    private TableView<InterpreteDao> tbvInterprete; // Cambiado a AlbumCancionDAO
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;

    public ListaInterprete() {
        CrearUI();
        this.setTitle("Interpretacion");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitHeight(50);

        Button btnAddRelacion = new Button();
        btnAddRelacion.setOnAction(event -> new FormInterprete(tbvInterprete, null)); // Cambiado a FormAlbumCancion
        btnAddRelacion.setGraphic(imv);
        tlbMenu.getItems().add(btnAddRelacion);

        tbvInterprete = new TableView<>();
        CrearTabla();

        vBox = new VBox(tlbMenu, tbvInterprete);
        escena = new Scene(vBox, 600, 400);
    }

    private void CrearTabla() {
        TableColumn<InterpreteDao, String> colAlbum = new TableColumn<>("Artista");
        colAlbum.setCellValueFactory(new PropertyValueFactory<>("artista"));

        TableColumn<InterpreteDao, String> colCancion = new TableColumn<>("Canción");
        colCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<InterpreteDao, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(param -> new ButtonCellInterprete("Editar"));

        TableColumn<InterpreteDao, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(param -> new ButtonCellInterprete("Eliminar"));

        tbvInterprete.getColumns().addAll(colAlbum, colCancion, tbcEditar, tbcEliminar);

        InterpreteDao dao = new InterpreteDao();
        tbvInterprete.setItems(dao.selectAll());
    }
}
