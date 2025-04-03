package handlers;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/// Clase que maneja la lectura y escritura de archivos en distintos formatos.
/// Soporta CSV y JSON.
public class FileHandler {

    /// Lee un archivo de datos y lo convierte en una lista de mapas.
    /// Identifica el formato del archivo (CSV o JSON) según su extensión.
    /// @param filePath Ruta del archivo a leer.
    /// @return Lista de mapas con los datos del archivo o null si el formato no es soportado
    public static List<Map<String, String>> readFileData(Path filePath) {
        String extension = getFileExtension(filePath.toString());
        return switch (extension) {
            case "csv" -> CSVHandler.readCSV(filePath);
            case "json" -> JSONHandler.readJSON(filePath);
            default -> {
                System.out.println("Formato no soportado.");
                yield null;
            }
        };
    }

    /// Escribe datos en un archivo en el formato especificado (CSV o JSON)
    /// @param filePath Ruta del archivo donde se guardarán los datos.
    /// @param data Lista de mapas con los datos a escribir
    /// @param format Formato del archivo de salida (csv o json).
    public static void writeFile(Path filePath, List<Map<String, String>> data, String format) {
        switch (format) {
            case "csv" -> CSVHandler.writeCSV(filePath, data);
            case "json" -> JSONHandler.writeJSON(filePath, data);
            default -> System.out.println("Formato no válido.");
        }
    }
    
    /// Obtiene la extensión de un archivo a partir de su nombre.
    /// @param filename Nombre o ruta del archivo.
    /// @return Extensión del archivo (sin el punto) o una cadena vacía si no tiene extensión
    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}
