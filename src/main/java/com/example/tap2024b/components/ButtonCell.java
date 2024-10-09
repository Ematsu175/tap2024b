package com.example.tap2024b.components;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

public class ButtonCell extends TableCell<ClienteDAO, String> {
    Button btnCelda;

    public ButtonCell(String str){
        btnCelda = new Button(str);
    }

    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}
