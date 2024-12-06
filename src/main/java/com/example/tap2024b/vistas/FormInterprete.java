package com.example.tap2024b.vistas;

import com.example.tap2024b.models.*;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormInterprete extends Stage {
    private ComboBox<ArtistaDAO> cmbArtista;
    private ComboBox<CancionDAO> cmbCancion;
    private InterpreteDao objInterprete;
    private TableView<InterpreteDao> tbvInterprete;

    public FormInterprete(TableView<InterpreteDao> tbv, InterpreteDao obj) {
        tbvInterprete = tbv;
        objInterprete = obj;
        CrearUI();

        if (obj != null) {
            objInterprete = obj;

            // Establece los valores originales
            objInterprete.setOriginalIdArtista(obj.getId_artista());
            objInterprete.setOriginalIdCancion(obj.getId_cancion());

            cargarArtistas();
            cargarCanciones();
            this.setTitle("Editar Relación");
        } else {
            objInterprete = new InterpreteDao();
            this.setTitle("Nueva Relación");
        }

        this.setScene(new Scene(CrearLayout(), 300, 200));
        this.show();
    }

    private void CrearUI() {
        cmbArtista = new ComboBox<>();
        cmbCancion = new ComboBox<>();
        cargarArtistas(); // Cargar álbumes en el ComboBox
        cargarCanciones(); // Cargar canciones en el ComboBox
    }

    private VBox CrearLayout() {
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> Guardar());

        VBox layout = new VBox(cmbArtista, cmbCancion, btnGuardar);
        layout.setPadding(new Insets(10));
        layout.setSpacing(10);

        return layout;
    }

    private void Guardar() {
        ArtistaDAO selectedArtista = cmbArtista.getValue();
        CancionDAO selectedCancion = cmbCancion.getValue();

        if (selectedArtista == null || selectedCancion == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("Selección incompleta");
            alerta.setContentText("Por favor, selecciona un álbum y una canción.");
            alerta.showAndWait();
            return;
        }

        objInterprete.setId_artista(selectedArtista.getId_artista());
        objInterprete.setId_cancion(selectedCancion.getId_cancion());

        String mensaje;
        Alert.AlertType tipoAlerta;

        if (objInterprete.getOriginalIdArtista() > 0 && objInterprete.getOriginalIdCancion() > 0) {
            objInterprete.update();
            mensaje = "Relación actualizada correctamente.";
            tipoAlerta = Alert.AlertType.INFORMATION;
        } else {
            int filasInsertadas = objInterprete.insert();
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
        tbvInterprete.setItems(objInterprete.selectAll());
        tbvInterprete.refresh();
        this.close();
    }



    private void cargarArtistas() {
        ArtistaDAO dao = new ArtistaDAO();
        ObservableList<ArtistaDAO> lista = dao.selectAll();
        cmbArtista.setItems(lista);

        if (objInterprete != null) {
            lista.stream()
                    .filter(artista -> artista.getId_artista() == objInterprete.getId_artista())
                    .findFirst()
                    .ifPresent(artista -> cmbArtista.getSelectionModel().select(artista));
        }
    }

    private void cargarCanciones() {
        CancionDAO dao = new CancionDAO();
        ObservableList<CancionDAO> lista = dao.selectAll();
        cmbCancion.setItems(lista);

        if (objInterprete != null) {
            lista.stream()
                    .filter(cancion -> cancion.getId_cancion() == objInterprete.getId_cancion())
                    .findFirst()
                    .ifPresent(cancion -> cmbCancion.getSelectionModel().select(cancion));
        }
    }
}
