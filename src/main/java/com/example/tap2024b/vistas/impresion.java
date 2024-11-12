package com.example.tap2024b.vistas;

import com.example.tap2024b.models.Tareas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class impresion extends Stage {
    private Button btnAgregarTarea;
    private Button btnApagar;
    private ProgressBar pgbBarra;
    private TableView tbvTareas;
    private ObservableList<Tareas> tareasList = FXCollections.observableArrayList();
    private int noArchivo, numHojas;
    private String nombreArchivo;
    private LocalDate fecha;
    private LocalTime hora;
    private Random random = new Random();
    private HBox hbxBotones;
    private VBox vbxBotonesTabla;
    Scene escena;
    private Timeline timelineProgress;
    private boolean btnEncendidoApagado = true; // Por defecto, el simulador estará encendido

    public impresion(){
        CrearUI();
        this.setTitle("Impresora");
        this.setScene(escena);
        this.show();
        escena.getStylesheets().add(getClass().getResource("/styles/impresora.css").toExternalForm());
        iniciarHiloMonitoreo();
    }

    public void CrearUI(){
        btnAgregarTarea = new Button("Agregar Tarea");
        btnAgregarTarea.getStyleClass().add("btn-AgregarTarea");
        btnApagar = new Button("Apagar");
        btnApagar.getStyleClass().add("btn-encendderapagar");

        pgbBarra = new ProgressBar();
        tbvTareas = new TableView();
        TableColumn<Tareas, Integer> noArchivoCol = new TableColumn<>("No. Archivo");
        noArchivoCol.setCellValueFactory(new PropertyValueFactory<>("noArchivo"));

        TableColumn<Tareas, String> nombreArchivoCol = new TableColumn<>("Nombre Archivo");
        nombreArchivoCol.setCellValueFactory(new PropertyValueFactory<>("nombreArchivo"));

        TableColumn<Tareas, Integer> numHojasCol = new TableColumn<>("Num. Hojas");
        numHojasCol.setCellValueFactory(new PropertyValueFactory<>("numHojas"));

        TableColumn<Tareas, LocalDate> fechaCol = new TableColumn<>("Fecha");
        fechaCol.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<Tareas, LocalTime> horaCol = new TableColumn<>("Hora");
        horaCol.setCellValueFactory(new PropertyValueFactory<>("hora"));

        tbvTareas.getColumns().addAll(noArchivoCol, nombreArchivoCol, numHojasCol, fechaCol, horaCol);
        tbvTareas.setItems(tareasList);


        btnAgregarTarea.setOnAction(event -> AgregarTareas());
        btnApagar.setOnAction(event-> EncendiderApagarImpresora());

        hbxBotones = new HBox(btnAgregarTarea, btnApagar);
        hbxBotones.setPadding(new Insets(20));
        hbxBotones.setSpacing(20);
        vbxBotonesTabla = new VBox(hbxBotones, pgbBarra, tbvTareas);
        escena = new Scene(vbxBotonesTabla, 800, 800);

    }

    private void EncendiderApagarImpresora() {
        btnEncendidoApagado = !btnEncendidoApagado; // Cambia el estado del simulador

        if (btnEncendidoApagado) {
            btnApagar.setText("Apagar");
            if (!tareasList.isEmpty()) {
                iniciarBarraProgreso();
            }
        } else {
            btnApagar.setText("Encender");
            if (timelineProgress != null) {
                timelineProgress.stop();
            }
        }
    }

    private void AgregarTareas(){
        noArchivo = random.nextInt(100) + 1;
        numHojas = random.nextInt(6) + 10;
        hora = LocalTime.now();
        fecha = LocalDate.now();

        String horaS = String.valueOf(hora);
        String fechaS = String.valueOf(fecha);
        nombreArchivo = fechaS + "_" + horaS + ".pdf";

        String tarea = (noArchivo+","+ nombreArchivo+","+ numHojas+","+ fecha+","+ hora);
        System.out.println(tarea);

        Tareas nuevaTarea = new Tareas(noArchivo, nombreArchivo, numHojas, fecha, hora);
        tareasList.add(nuevaTarea);

        if (tareasList.size() == 1) {
            iniciarBarraProgreso();
        }

    }
    private void iniciarBarraProgreso() {
        if (!btnEncendidoApagado || tareasList.isEmpty()) {
            return;
        }

        if (timelineProgress != null && timelineProgress.getStatus() == Timeline.Status.RUNNING) {
            return; // Evita iniciar múltiples timelines si ya hay uno ejecutándose
        }

        Tareas tareaActual = tareasList.get(0);
        pgbBarra.setProgress(0);

        int tiempoTotal = tareaActual.getNumHojas();
        double incremento = 1.0 / (tiempoTotal * 10);

        timelineProgress = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            double progresoActual = pgbBarra.getProgress();
            pgbBarra.setProgress(progresoActual + incremento);

            if (pgbBarra.getProgress() >= 1.0) {
                timelineProgress.stop();
                eliminarPrimeraTarea();

                if (btnEncendidoApagado && !tareasList.isEmpty()) {
                    Platform.runLater(() -> this.iniciarBarraProgreso()); // Procesa la siguiente tarea
                }
            }
        }));
        timelineProgress.setCycleCount(Timeline.INDEFINITE);
        timelineProgress.play();
    }

    private void eliminarPrimeraTarea() {
        if (!tareasList.isEmpty()) {
            tareasList.remove(0);
        }
    }

    private void iniciarHiloMonitoreo() {
        System.out.println("Hilo monitoreo Iniciado");
        Thread hiloMonitoreo = new Thread(() -> {
            while (true) {
                if (!tareasList.isEmpty() && pgbBarra.getProgress() == 0) {
                    Platform.runLater(() -> this.iniciarBarraProgreso()); // Inicia la barra de progreso para la primera tarea
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        hiloMonitoreo.setDaemon(true);
        hiloMonitoreo.start();
    }

}
