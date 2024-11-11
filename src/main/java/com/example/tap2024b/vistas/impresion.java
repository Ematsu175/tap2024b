package com.example.tap2024b.vistas;

import com.example.tap2024b.models.Tareas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    public impresion(){
        CrearUI();
        this.setTitle("Impresora");
        this.setScene(escena);
        this.show();
    }

    public void CrearUI(){
        btnAgregarTarea = new Button("Agregar Tarea");
        btnApagar = new Button("Apagar");
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

        hbxBotones = new HBox(btnAgregarTarea, btnApagar);
        vbxBotonesTabla = new VBox(hbxBotones, pgbBarra, tbvTareas);
        escena = new Scene(vbxBotonesTabla, 800, 800);

    }

    private void AgregarTareas(){
        noArchivo = random.nextInt(100) + 1;
        numHojas = random.nextInt(100) + 1;
        hora = LocalTime.now();
        fecha = LocalDate.now();

        String horaS = String.valueOf(hora);
        String fechaS = String.valueOf(fecha);
        nombreArchivo = fechaS + "_" + horaS + ".pdf";

        String tarea = (noArchivo+","+ nombreArchivo+","+ numHojas+","+ fecha+","+ hora);
        System.out.println(tarea);

        Tareas nuevaTarea = new Tareas(noArchivo, nombreArchivo, numHojas, fecha, hora);
        tareasList.add(nuevaTarea);

        // Si esta es la primera tarea, comienza a cargar la barra de progreso.
        if (tareasList.size() == 1) {
            iniciarBarraProgreso();
        }

    }
    private void iniciarBarraProgreso() {
        pgbBarra.setProgress(0); // Reinicia la barra de progreso

        int tiempoTotal = random.nextInt(11) + 5; // Tiempo total entre 5 y 15 segundos para la tarea
        double incremento = 1.0 / (tiempoTotal * 10); // Incremento de progreso cada 100ms

        timelineProgress = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            double progresoActual = pgbBarra.getProgress();
            pgbBarra.setProgress(progresoActual + incremento);

            if (pgbBarra.getProgress() >= 1.0) {
                timelineProgress.stop();
                eliminarPrimeraTarea();
                if (!tareasList.isEmpty()) {
                    iniciarBarraProgreso(); // Inicia la barra para la siguiente tarea
                }
            }
        }));
        timelineProgress.setCycleCount(Timeline.INDEFINITE);
        timelineProgress.play();
    }

    private void eliminarPrimeraTarea() {
        if (!tareasList.isEmpty()) {
            tareasList.remove(0); // Elimina la tarea más antigua
        }
    }
}
