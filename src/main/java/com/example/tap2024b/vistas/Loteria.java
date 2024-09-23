package com.example.tap2024b.vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

import java.util.Arrays;
import java.util.Collections;

public class Loteria extends Stage {
    private HBox hBoxMain, hBoxButtons;
    private VBox vbxTablilla, vbxMazo;
    private Button btnAnterior, btnSiguiente, btnIniciar;
    private Label lblTimer;
    private GridPane gdpTablilla;
    private ImageView imvMazo;
    private Scene escena;
    private String[][] conjuntosDeImagenes = {
            {
                    "barril.jpeg", "botella.jpeg", "catrin.jpeg", "chavorruco.jpeg",
                    "alacran.jpg", "luchador.jpeg", "maceta.jpeg", "rosa.jpeg",
                    "tacos.jpeg", "venado.jpeg", "luna.jpg", "corazon.jpg"
            },
            {
                    "catrin.jpeg", "chavorruco.jpeg", "arana.jpg", "luchador.jpeg",
                    "maceta.jpeg", "rosa.jpeg", "diablo.jpg", "venado.jpeg",
                    "barril.jpeg", "botella.jpeg", "luna.jpg", "pescado.jpg"
            },
            {
                    "tacos.jpeg", "venado.jpeg", "barril.jpeg", "botella.jpeg",
                    "catrin.jpeg", "chavorruco.jpeg", "sol.jpg", "luchador.jpeg",
                    "maceta.jpeg", "rosa.jpeg", "tacos.jpeg", "sandia.jpg"
            },
            {
                    "sandia.jpg", "venado.jpeg", "barril.jpeg", "cotorro.jpg",
                    "gallo.jpg", "chavorruco.jpeg", "sol.jpg", "luchador.jpeg",
                    "maceta.jpeg", "estrella.jpg", "tacos.jpeg", "sandia.jpg"
            },
            {
                    "alacran.jpg", "arana.jpg", "corazon.jpg", "cotorro.jpg",
                    "diablo.jpg", "estrella.jpg", "sol.jpg", "pescado.jpg",
                    "maceta.jpeg", "melon.jpg", "tacos.jpeg", "sandia.jpg"
            }
    };

    private String[] imagenesMazo = {"alacran.jpg","arana.jpg","barril.jpeg","botella.jpeg","catrin.jpeg","chavorruco.jpeg","concha.jpeg","corazon.jpg","cotorro.jpg",
                                     "diablo.jpg","estrella.jpg","gallo.jpg","luchador.jpeg","luna.jpg","maceta.jpeg","melon.jpg","pescado.jpg","rosa.jpeg",
                                     "sandia.jpg","sol.jpg","tacos.jpeg","venado.jpeg"};
    private int indiceActual = 0;
    private Button[][] arrBtnTab;
    private Panel pnlPrincipal;
    private boolean temporizadorActivo = false;
    private int indiceImagenActual = 0;
    private Timeline timeline;
    public Loteria(){
        CrearUI();
        this.setTitle("Loteria Mexicana");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        gdpTablilla= new GridPane();
        CrearTablilla(0);
        btnAnterior = new Button("Anterior");
        btnAnterior.setOnAction(e -> anteriorTablilla());
        btnSiguiente = new Button("Siguiente");
        btnSiguiente.setOnAction(e -> siguienteTablilla());
        hBoxButtons = new HBox(btnAnterior, btnSiguiente);
        vbxTablilla = new VBox(gdpTablilla, hBoxButtons);

        CrearMazo();

        hBoxMain = new HBox(vbxTablilla, vbxMazo);
        pnlPrincipal = new Panel("Loteria Mexicana");
        pnlPrincipal.getStyleClass().add("panel-primary");
        pnlPrincipal.setBody(hBoxMain);
        hBoxMain.setSpacing(20);
        hBoxMain.setPadding(new Insets(20));
        escena = new Scene(pnlPrincipal, 900,650);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearMazo() {
        Image imgMazo = new Image(getClass().getResource("/images/loteria/dorso.jpeg").toString());
        lblTimer = new Label("00:00");
        imvMazo = new ImageView(imgMazo);
        imvMazo.setFitHeight(300);
        imvMazo.setFitWidth(200);
        btnIniciar = new Button("Iniciar Juego");
        btnIniciar.setOnAction(event -> {
            iniciarTemporizador();
            deshabilitarBotones();
        });
        //btnIniciar.getStyleClass().setAll("btn-sm","bnt-danger");
        vbxMazo = new VBox(lblTimer, imvMazo,btnIniciar);
        vbxMazo.setSpacing(20);
    }

    private void CrearTablilla(int indiceConjunto) {

        arrBtnTab = new Button[4][4];
        Image img;
        ImageView imv;
        String[] arrImg = conjuntosDeImagenes[indiceConjunto];
        int imageIndex = 0;

        gdpTablilla.getChildren().clear();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (imageIndex < arrImg.length) {
                    img = new Image(getClass().getResource("/images/loteria/" + arrImg[imageIndex]).toString());
                    imv = new ImageView(img);
                    imv.setFitWidth(100);
                    imv.setFitHeight(125);

                    arrBtnTab[j][i] = new Button();
                    arrBtnTab[j][i].setGraphic(imv);

                    arrBtnTab[j][i].setUserData(arrImg[imageIndex]);
                    arrBtnTab[j][i].setOnAction(event -> manejarClicBoton(event));

                    gdpTablilla.add(arrBtnTab[j][i], j, i);
                    imageIndex++;
                } else {
                    arrBtnTab[j][i] = null;
                }
            }
        }
    }

    public void siguienteTablilla() {
        if (indiceActual == conjuntosDeImagenes.length - 1) {
            indiceActual = 0;
        } else {
            indiceActual++;
        }
        CrearTablilla(indiceActual);
    }

    public void anteriorTablilla() {
        if (indiceActual == 0) {
            indiceActual = conjuntosDeImagenes.length - 1;
        } else {
            indiceActual--;
        }
        CrearTablilla(indiceActual);
    }

    private void iniciarTemporizador() {
        final int[] tiempoRestante = {2}; // Tiempo en segundos
        temporizadorActivo = true; // El temporizador está activo
        indiceImagenActual = 0; // Reiniciar el índice de imágenes

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (tiempoRestante[0] > 0) {
                tiempoRestante[0]--; // Decrementar el tiempo
            } else {
                // Reiniciar el temporizador
                tiempoRestante[0] = 2; // Reiniciar a 10 segundos

                // Mostrar la siguiente imagen en orden solo si no hemos llegado al final
                if (indiceImagenActual < imagenesMazo.length) {
                    String nuevaImagen = imagenesMazo[indiceImagenActual];
                    Image imgNueva = new Image(getClass().getResource("/images/loteria/" + nuevaImagen).toString());
                    imvMazo.setImage(imgNueva);

                    // Verificar condiciones de pérdida
                    if (nuevaImagen.equals("alacran.jpg") && indiceImagenActual > 0) {
                        mostrarMensajePerdida(); // Mostrar mensaje de pérdida
                    }
                    if (nuevaImagen.equals("venado.jpg") && tiempoRestante[0] == 0) {
                        if (!todosBotonesDeshabilitados()) {
                            mostrarMensajePerdida();
                        }
                    }

                    indiceImagenActual++;
                } else {
                    // Si hemos mostrado todas las imágenes
                    if (indiceImagenActual == imagenesMazo.length) {
                        timeline.stop();
                        mostrarMensajePerdida();
                    }
                }
            }

            // Mostrar el tiempo restante
            lblTimer.setText(String.format("%02d:%02d", tiempoRestante[0] / 60, tiempoRestante[0] % 60));
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    private String obtenerImagenAleatoria() {
        int index = (int) (Math.random() * imagenesMazo.length); // Seleccionar un índice aleatorio
        return imagenesMazo[index];
    }

    private void deshabilitarBotones() {
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnIniciar.setDisable(true);
    }

    private void manejarClicBoton(ActionEvent event) {
        Button botonPresionado = (Button) event.getSource();
        String cartaActual = obtenerImagenActual();

        // Verificar si hay coincidencia
        if (botonPresionado.getUserData().equals(cartaActual)) {
            botonPresionado.setDisable(true);

            // Verificar si todos los botones están deshabilitados
            if (todosBotonesDeshabilitados()) {
                detenerTemporizador();
                mostrarMensajeVictoria();
            }
        }
    }

    private String obtenerImagenActual() {
        // Retornar el nombre de la imagen actual en el mazo
        return imvMazo.getImage().getUrl().substring(imvMazo.getImage().getUrl().lastIndexOf('/') + 1);
    }

    private boolean todosBotonesDeshabilitados() {
        for (Button[] fila : arrBtnTab) {
            for (Button boton : fila) {
                if (boton != null && !boton.isDisabled()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void detenerTemporizador() {
        temporizadorActivo = false;
    }

    private void mostrarMensajeVictoria() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("¡Victoria!");
        alert.setHeaderText(null);
        alert.setContentText("¡GANASTE!");
        alert.showAndWait();
    }

    private void mostrarMensajePerdida() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Resultado");
            alert.setHeaderText(null);
            alert.setContentText("¡Has perdido!");
            alert.showAndWait();
        });
    }


}
