package com.example.tap2024b.components;

import com.example.tap2024b.models.AlbumCancionDAO;
import com.example.tap2024b.models.InterpreteDao;
import com.example.tap2024b.vistas.FormAlbumCancion;
import com.example.tap2024b.vistas.FormInterprete;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

public class ButtonCellInterprete extends TableCell<InterpreteDao, String> {
    private final Button btnCelda;

    public ButtonCellInterprete(String texto) {
        btnCelda = new Button(texto);
        btnCelda.setOnAction(event -> manejarEvento(texto));
    }

    private void manejarEvento(String texto) {
        TableView<InterpreteDao> tabla = getTableView();
        InterpreteDao obj = tabla.getItems().get(getIndex());

        if (texto.equals("Editar")) {
            new FormInterprete(tabla, obj); // Abre el formulario para editar
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
