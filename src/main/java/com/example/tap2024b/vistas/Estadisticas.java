package com.example.tap2024b.vistas;

import com.example.tap2024b.models.Conexion;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Estadisticas extends Stage {
    public Estadisticas() {
        VBox vbox = new VBox();

        BarChart<String, Number> ventasMes = crearGraficaVentasMes();
        BarChart<String, Number> artistasMasVendidos = crearGraficaArtistasMasVendidos();
        PieChart cancionesMasVendidas = crearGraficaCancionesMasVendidas();

        vbox.getChildren().addAll(ventasMes, artistasMasVendidos, cancionesMasVendidas);
        Scene escena = new Scene(vbox, 800, 600);

        this.setScene(escena);
        this.setTitle("Estadísticas");
        this.show();
    }

    private BarChart<String, Number> crearGraficaVentasMes() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Días del Mes");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Cantidad de Ventas");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ventas Realizadas en el Mes");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ventas");

        String query = "SELECT DAY(fecha) AS dia, COUNT(*) AS total " +
                "FROM venta WHERE MONTH(fecha) = MONTH(CURRENT_DATE()) " +
                "GROUP BY DAY(fecha)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String dia = String.valueOf(rs.getInt("dia"));
                int total = rs.getInt("total");
                dataSeries.getData().add(new XYChart.Data<>(dia, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(dataSeries);
        return barChart;
    }

    private BarChart<String, Number> crearGraficaArtistasMasVendidos() {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Artistas");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ventas");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Artistas con Más Ventas en el Mes");

        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        dataSeries.setName("Ventas");

        String query = "select a.artista as artista, COUNT(*) as total " +
                "from venta_detalle vd join cancion c on vd.id_cancion=c.id_cancion " +
                "join interpretacion i on c.id_cancion=i.id_cancion " +
                "join artista a on i.id_artista=a.id_artista " +
                "join venta v on vd.id_venta=v.id_venta " +
                "where MONTH(v.fecha)=MONTH(CURRENT_DATE()) group by 1 order by total desc limit 5;";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String artista = rs.getString("artista");
                int total = rs.getInt("total");
                dataSeries.getData().add(new XYChart.Data<>(artista, total));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        barChart.getData().add(dataSeries);
        return barChart;
    }

    private PieChart crearGraficaCancionesMasVendidas() {
        PieChart pieChart = new PieChart();
        pieChart.setTitle("Canciones Más Vendidas");

        String query = "SELECT c.cancion, COUNT(*) AS total " +
                "FROM venta_detalle vd " +
                "JOIN cancion c ON vd.id_cancion = c.id_cancion " +
                "JOIN venta v ON vd.id_venta = v.id_venta " +
                "WHERE MONTH(v.fecha) = MONTH(CURRENT_DATE()) " +
                "GROUP BY c.cancion " +
                "ORDER BY total DESC LIMIT 5";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String cancion = rs.getString("cancion");
                int total = rs.getInt("total");
                PieChart.Data data = new PieChart.Data(cancion, total);
                pieChart.getData().add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pieChart;
    }
}
