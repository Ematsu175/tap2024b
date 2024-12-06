package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.CancionDAO;
import com.example.tap2024b.models.GeneroDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCancion extends Stage {
    private Scene escena;
    private TextField txtCancion, txtDuracion, txtCosto;
    private ComboBox<GeneroDAO> cmbGenero; // ComboBox para los géneros
    private CancionDAO objCancion;
    private final TableView<CancionDAO> tbvCancion;

    public FormCancion(TableView<CancionDAO> tbv, CancionDAO objc) {
        tbvCancion = tbv;
        CrearUI();
        if (objc != null) {
            this.objCancion = objc;
            txtCancion.setText(objc.getCancion());
            txtDuracion.setText(objc.getDuracion());
            txtCosto.setText(String.valueOf(objc.getCosto()));
            // Seleccionar el género actual en el ComboBox
            GeneroDAO generoSeleccionado = cmbGenero.getItems().stream()
                    .filter(g -> g.getId_genero() == objc.getId_genero())
                    .findFirst()
                    .orElse(null);
            cmbGenero.setValue(generoSeleccionado);

            this.setTitle("Editar Canción");
        } else {
            this.objCancion = new CancionDAO();
            this.setTitle("Insertar Canción");
        }
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtCancion = new TextField();
        txtCancion.setPromptText("Nombre de la Canción");
        txtDuracion = new TextField();
        txtDuracion.setPromptText("Duración");
        txtCosto = new TextField();
        txtCosto.setPromptText("Costo $$$");

        // ComboBox para los géneros
        cmbGenero = new ComboBox<>();
        cargarGeneros(); // Llenar el ComboBox con los géneros de la base de datos

        Button btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> Guardar());

        VBox vbox = new VBox(txtCancion, txtDuracion, txtCosto, cmbGenero, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 300, 300);
    }

    private void cargarGeneros() {
        GeneroDAO generoDAO = new GeneroDAO();
        ObservableList<GeneroDAO> listaGeneros = generoDAO.selectAll();
        cmbGenero.setItems(listaGeneros);
    }

    private void Guardar() {
        try {
            if (txtCancion.getText().isEmpty() || txtDuracion.getText().isEmpty() ||
                    txtCosto.getText().isEmpty() || cmbGenero.getValue() == null) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

            // Validar que el costo sea numérico
            float costo = Float.parseFloat(txtCosto.getText());
            if (costo < 0) {
                throw new IllegalArgumentException("El costo no puede ser negativo.");
            }

            objCancion.setCancion(txtCancion.getText());
            objCancion.setDuracion(txtDuracion.getText());
            objCancion.setCosto(costo);
            objCancion.setId_genero(cmbGenero.getValue().getId_genero());

            String msj;
            Alert.AlertType type;

            if (objCancion.getId_cancion() > 0) {
                objCancion.update();
                msj = "Registro actualizado correctamente.";
                type = Alert.AlertType.INFORMATION;
            } else {
                if (objCancion.insert() > 0) {
                    msj = "Registro insertado correctamente.";
                    type = Alert.AlertType.INFORMATION;
                } else {
                    msj = "Ocurrió un error al insertar.";
                    type = Alert.AlertType.ERROR;
                }
            }

            // Mostrar mensaje de alerta
            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();

            // Actualizar el TableView
            tbvCancion.setItems(objCancion.selectAll());
            tbvCancion.refresh();

            // Cerrar el formulario
            this.close();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de formato", "Costo debe ser un valor numérico.");
        } catch (IllegalArgumentException e) {
            mostrarAlerta("Campos obligatorios", e.getMessage());
        }
    }


    private void mostrarAlerta(String titulo, String contenido) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
