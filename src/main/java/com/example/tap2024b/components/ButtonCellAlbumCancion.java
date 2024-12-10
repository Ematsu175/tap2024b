package com.example.tap2024b.components;

import com.example.tap2024b.models.AlbumCancionDAO;
import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.vistas.FormAlbumCancion;
import com.example.tap2024b.vistas.FormArtista;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCellAlbumCancion extends TableCell<AlbumCancionDAO, String> {
    private final Button btnCelda;

    public ButtonCellAlbumCancion(String texto) {
        btnCelda = new Button(texto);

        if (texto.equals("Editar")) {
            btnCelda.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        } else if (texto.equals("Eliminar")) {
            btnCelda.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }

        btnCelda.setOnAction(event -> manejarEvento(texto));
    }

    private void manejarEvento(String texto) {
        TableView<AlbumCancionDAO> tabla = getTableView();
        AlbumCancionDAO obj = tabla.getItems().get(getIndex());

        if (texto.equals("Editar")) {
            new FormAlbumCancion(tabla, obj); // Abre el formulario para editar
        } else if (texto.equals("Eliminar")) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("¿Deseas eliminar esta relación?");

            alerta.showAndWait().ifPresent(response -> {
                if (response.getButtonData().isDefaultButton()) {
                    obj.delete(); // Elimina la relación de la base de datos
                    tabla.setItems(obj.selectAll()); // Actualiza el TableView
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda); // Muestra el botón en la celda
        } else {
            setGraphic(null);
        }
    }
}
