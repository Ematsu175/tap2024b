package com.example.tap2024b.vistas;

import com.example.tap2024b.models.AlbumCancionDAO;
import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.CancionDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormAlbumCancion extends Stage {
    private ComboBox<AlbumDAO> cmbAlbum;
    private ComboBox<CancionDAO> cmbCancion;
    private AlbumCancionDAO objAlbumCancion;
    private final TableView<AlbumCancionDAO> tbvAlbumCancion;

    public FormAlbumCancion(TableView<AlbumCancionDAO> tbv, AlbumCancionDAO obj) {
        tbvAlbumCancion = tbv;
        objAlbumCancion = obj;
        CrearUI();

        if (obj != null) {
            objAlbumCancion = obj;

            // Establece los valores originales
            objAlbumCancion.setOriginalIdAlbum(obj.getId_album());
            objAlbumCancion.setOriginalIdCancion(obj.getId_cancion());

            cargarAlbums();
            cargarCanciones();
            this.setTitle("Editar Relación");
        } else {
            objAlbumCancion = new AlbumCancionDAO();
            this.setTitle("Nueva Relación");
        }

        this.setScene(new Scene(CrearLayout(), 300, 200));
        this.show();
    }

    private void CrearUI() {
        cmbAlbum = new ComboBox<>();
        cmbCancion = new ComboBox<>();
        cargarAlbums(); // Cargar álbumes en el ComboBox
        cargarCanciones(); // Cargar canciones en el ComboBox
    }

    private VBox CrearLayout() {
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> Guardar());

        VBox layout = new VBox(cmbAlbum, cmbCancion, btnGuardar);
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        return layout;
    }

    private void Guardar() {
        AlbumDAO selectedAlbum = cmbAlbum.getValue();
        CancionDAO selectedCancion = cmbCancion.getValue();

        if (selectedAlbum == null || selectedCancion == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Selección incompleta");
            alerta.setContentText("Por favor, selecciona un álbum y una canción.");
            alerta.showAndWait();
            return;
        }

        objAlbumCancion.setId_album(selectedAlbum.getId_album());
        objAlbumCancion.setId_cancion(selectedCancion.getId_cancion());

        String mensaje;
        Alert.AlertType tipoAlerta;

        if (objAlbumCancion.getOriginalIdAlbum() > 0 && objAlbumCancion.getOriginalIdCancion() > 0) {
            objAlbumCancion.update();
            mensaje = "Relación actualizada correctamente.";
            tipoAlerta = Alert.AlertType.INFORMATION;
        } else {
            int filasInsertadas = objAlbumCancion.insert();
            if (filasInsertadas > 0) {
                mensaje = "Relación insertada correctamente.";
                tipoAlerta = Alert.AlertType.INFORMATION;
            } else {
                mensaje = "Error al insertar la relación. Revisa los datos.";
                tipoAlerta = Alert.AlertType.ERROR;
            }
        }

        // Mostrar el mensaje de resultado
        Alert alerta = new Alert(tipoAlerta);
        alerta.setTitle("Resultado");
        alerta.setContentText(mensaje);
        alerta.showAndWait();

        // Actualizar la tabla
        tbvAlbumCancion.setItems(objAlbumCancion.selectAll());
        tbvAlbumCancion.refresh();
        this.close();
    }



    private void cargarAlbums() {
        AlbumDAO dao = new AlbumDAO();
        ObservableList<AlbumDAO> lista = dao.selectAll();
        cmbAlbum.setItems(lista);

        if (objAlbumCancion != null) {
            lista.stream()
                    .filter(album -> album.getId_album() == objAlbumCancion.getId_album())
                    .findFirst()
                    .ifPresent(album -> cmbAlbum.getSelectionModel().select(album));
        }
    }

    private void cargarCanciones() {
        CancionDAO dao = new CancionDAO();
        ObservableList<CancionDAO> lista = dao.selectAll();
        cmbCancion.setItems(lista);

        if (objAlbumCancion != null) {
            lista.stream()
                    .filter(cancion -> cancion.getId_cancion() == objAlbumCancion.getId_cancion())
                    .findFirst()
                    .ifPresent(cancion -> cmbCancion.getSelectionModel().select(cancion));
        }
    }

}
