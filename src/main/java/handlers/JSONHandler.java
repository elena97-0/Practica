package handlers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/// Clase que maneja la lectura y escritura de archivos JSON
public class JSONHandler {

    /// Lee un archivo JSON y lo convierte en una lista de mapas (diccionarios).
    /// Cada objeto JSON se almacena como un mapa donde la clave es el nombre del campo
    /// y el valor es el dato correspondiente
    /// @param filePath filePath Ruta del archivo JSON a leer
    /// @return Lista de mapas con los datos del archivo o null si hay un error
    public static List<Map<String, String>> readJSON(Path filePath) {
        try {
            String content = Files.readString(filePath);
            JSONArray jsonArray = new JSONArray(content);
            List<Map<String, String>> dataList = new ArrayList<>();
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                Map<String, String> row = new HashMap<>();
                for (String key : jsonObject.keySet()) {
                    row.put(key, jsonObject.getString(key));
                }
                dataList.add(row);
            }
            return dataList;
        } catch (IOException e) {
            System.out.println("Error al leer JSON.");
            return null;
        }
    }
    
    /// Escribe una lista de mapas en un archivo JSON.
    /// @param filePath Ruta donde se guardará el archivo JSON
    /// @param data Lista de mapas con los datos a escribir
    public static void writeJSON(Path filePath, List<Map<String, String>> data) {
        JSONArray jsonArray = new JSONArray(data);
        try {
            Files.writeString(filePath, jsonArray.toString(4));
        } catch (IOException e) {
            System.out.println("Error al escribir JSON.");
        }
    }
}
