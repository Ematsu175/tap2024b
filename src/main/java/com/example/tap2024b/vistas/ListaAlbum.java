package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellAlbum;
import com.example.tap2024b.components.ButtonCellArtista;
import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ArtistaDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ListaAlbum extends Stage {
    private TableView<AlbumDAO> tbvAlbum;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;


    public ListaAlbum() {
        CrearUI();
        this.setTitle("Albums");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();
        ImageView imv = new ImageView(getClass().getResource("/images/banderaB.png").toString());
        imv.setFitHeight(50);
        imv.setFitHeight(50);
        Button btnAddCliente = new Button();
        btnAddCliente.setOnAction(event -> new FormAlbum(tbvAlbum, null));
        tlbMenu.getItems().add(btnAddCliente);
        btnAddCliente.setGraphic(imv);

        tbvAlbum = new TableView<>();

        CrearTable();
        vBox = new VBox(tlbMenu,tbvAlbum);
        escena = new Scene(vBox,500,250);
    }
    private void CrearTable() {
        AlbumDAO objAlbum = new AlbumDAO();

        // Columna para el nombre del álbum
        TableColumn<AlbumDAO, String> tbcAlbum = new TableColumn<>("Album");
        tbcAlbum.setCellValueFactory(new PropertyValueFactory<>("album"));

        // Columna para la fecha de lanzamiento
        TableColumn<AlbumDAO, String> tbcFechaLanzamiento = new TableColumn<>("Fecha de Lanzamiento");
        tbcFechaLanzamiento.setCellValueFactory(new PropertyValueFactory<>("fecha_lanzamiento"));

        // Columna para la imagen (debe ser de tipo ImageView)
        TableColumn<AlbumDAO, Image> tbcImagenAlbum = new TableColumn<>("Imagen");

        // Usamos un CellFactory para convertir el Image en ImageView
        tbcImagenAlbum.setCellValueFactory(new PropertyValueFactory<>("img_album"));  // img_album es de tipo Image

        // Convertir el Image en ImageView para mostrarlo en la tabla
        tbcImagenAlbum.setCellFactory(param -> new TableCell<AlbumDAO, Image>() {
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);  // Si la celda está vacía, no mostrar nada
                } else {
                    // Asegurarse de que la imagen se pueda visualizar en la tabla
                    ImageView imageView = new ImageView(item);  // Convertir Image a ImageView
                    imageView.setFitWidth(100);  // Ajustar el tamaño para la celda
                    imageView.setFitHeight(100);  // Ajustar el tamaño para la celda
                    setGraphic(imageView);  // Establecer el ImageView como el gráfico de la celda
                }
            }
        });


        // Botones para editar y eliminar
        TableColumn<AlbumDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAlbum("Editar");
            }

        });

        TableColumn<AlbumDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<AlbumDAO, String>, TableCell<AlbumDAO, String>>() {
            @Override
            public TableCell<AlbumDAO, String> call(TableColumn<AlbumDAO, String> albumDAOStringTableColumn) {
                return new ButtonCellAlbum("Eliminar");
            }
        });

        // Agregar todas las columnas a la tabla
        tbvAlbum.getColumns().addAll(tbcAlbum, tbcFechaLanzamiento, tbcImagenAlbum, tbcEditar, tbcEliminar);

        // Llenar la tabla con los datos de la base de datos
        tbvAlbum.setItems(objAlbum.selectAll());
    }

    public static class Data {
        private final Integer id;
        private final Image image;

        public Data(Integer id, Image image) {
            this.id = id;
            this.image = image;
        }

        public Integer getId() {
            return id;
        }

        public Image getImage() {
            return image;
        }
    }
}

