package handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

///Clase que maneja la lectura y escritura de archivos CSV.
public class CSVHandler {

    /// @param filePath Ruta del archivo CSV a leer.
    /// @return Lista de mapas con los datos del archivo.
    
    public static List<Map<String, String>> readCSV(Path filePath) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String[] headers = br.readLine().split(",");
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }
                dataList.add(row);
            }
        } catch (IOException e) {
            System.out.println("Error al leer CSV.");
        }
        return dataList;
    }

    /// Escribe una lista de mapas en un archivo CSV.
    /// @param filePath Ruta donde se guardará el archivo CSV
    /// @param data Lista de mapas que contienen los datos a escribir.
    
    public static void writeCSV(Path filePath, List<Map<String, String>> data) {
        try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
            if (data.isEmpty()) return;
            Set<String> headers = data.get(0).keySet();
            bw.write(String.join(",", headers));
            bw.newLine();
            for (Map<String, String> row : data) {
                bw.write(String.join(",", row.values()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al escribir CSV.");
        }
    }
}
