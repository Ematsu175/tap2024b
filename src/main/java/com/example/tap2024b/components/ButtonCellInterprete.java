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
        if (texto.equals("Editar")) {
            btnCelda.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        } else if (texto.equals("Eliminar")) {
            btnCelda.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }
        btnCelda.setOnAction(event -> manejarEvento(texto));
    }

    private void manejarEvento(String texto) {
        TableView<InterpreteDao> tabla = getTableView();
        InterpreteDao obj = tabla.getItems().get(getIndex());

        if (texto.equals("Editar")) {
            new FormInterprete(tabla, obj);
        } else if (texto.equals("Eliminar")) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmación");
            alerta.setHeaderText(null);
            alerta.setContentText("¿Deseas eliminar esta relación?");

            alerta.showAndWait().ifPresent(response -> {
                if (response.getButtonData().isDefaultButton()) {
                    obj.delete();
                    tabla.setItems(obj.selectAll());
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCelda);
        } else {
            setGraphic(null);
        }
    }
}
