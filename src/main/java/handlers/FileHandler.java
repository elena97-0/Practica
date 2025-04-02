package handlers;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FileHandler {
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

    public static void writeFile(Path filePath, List<Map<String, String>> data, String format) {
        switch (format) {
            case "csv" -> CSVHandler.writeCSV(filePath, data);
            case "json" -> JSONHandler.writeJSON(filePath, data);
            default -> System.out.println("Formato no válido.");
        }
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }
}
