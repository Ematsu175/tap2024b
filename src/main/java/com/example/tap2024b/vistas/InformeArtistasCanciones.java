package com.example.tap2024b.vistas;

import com.example.tap2024b.models.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class InformeArtistasCanciones {

    public InformeArtistasCanciones(){
        System.out.println("Iniciando generación de informe desde el menú...");
        generarReporte("C:/Users/emyva/Downloads/artistas_canciones.pdf");
    }

    public void generarReporte(String filePath) {
        Document document = new Document();
        try {
            System.out.println("Iniciando generación del reporte...");
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();
            System.out.println("Documento abierto.");

            Paragraph title = new Paragraph("Reporte de Artistas y Canciones");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            PdfPCell header1 = new PdfPCell(new Paragraph("Artista"));
            PdfPCell header2 = new PdfPCell(new Paragraph("Canción"));
            table.addCell(header1);
            table.addCell(header2);

            Map<String, String> artistasCanciones = obtenerArtistasYCanciones();
            for (Map.Entry<String, String> entry : artistasCanciones.entrySet()) {
                table.addCell(entry.getKey());
                table.addCell(entry.getValue());
            }

            document.add(table);
            System.out.println("Datos añadidos al documento.");

        } catch (Exception e) {
            System.err.println("Error al generar el reporte: " + e.getMessage());
            e.printStackTrace();
        } finally {
            document.close();
            System.out.println("Documento cerrado.");
        }
    }



    private Map<String, String> obtenerArtistasYCanciones() {
        Map<String, String> artistasCanciones = new HashMap<>();
        String query = "SELECT a.artista AS artista, c.cancion AS cancion FROM artista a " +
                "JOIN interpretacion i ON a.id_artista = i.id_artista " +
                "JOIN cancion c ON i.id_cancion = c.id_cancion";

        try (Connection conn = Conexion.connection;
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String artista = rs.getString("artista");
                String cancion = rs.getString("cancion");
                artistasCanciones.put(artista, cancion);
            }
            System.out.println("Datos obtenidos correctamente.");
        } catch (Exception e) {
            System.err.println("Error al obtener artistas y canciones: " + e.getMessage());
            e.printStackTrace();
        }
        return artistasCanciones;
    }

}
