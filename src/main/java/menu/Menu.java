package menu;

import handlers.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Path selectedFolder;
    private List<Map<String, String>> data;
    private String selectedFile;

    public void start() {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleMenuChoice(choice);
        }
    }

    private void showMenu() {
        System.out.println("\n--- Conversor de Archivos ---");
        if (selectedFolder != null) {
            System.out.println("Carpeta: " + selectedFolder);
            listFiles(selectedFolder);
        }
        if (selectedFile != null) {
            System.out.println("Archivo seleccionado: " + selectedFile);
        }
        System.out.println("1. Seleccionar carpeta");
        System.out.println("2. Leer fichero");
        System.out.println("3. Convertir archivo");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> selectFolder();
            case 2 -> readFile();
            case 3 -> convertFile();
            case 4 -> exitProgram();
            default -> System.out.println("Opción no válida.");
        }
    }

    private void selectFolder() {
        System.out.print("Ingrese la ruta de la carpeta: ");
        Path path = Paths.get(scanner.nextLine());
        if (Files.isDirectory(path)) {
            selectedFolder = path;
        } else {
            System.out.println("Carpeta no válida.");
        }
    }

    private void listFiles(Path folder) {
        try {
            Files.list(folder).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error al listar archivos.");
        }
    }

    private void readFile() {
        if (selectedFolder == null) {
            System.out.println("Seleccione una carpeta primero.");
            return;
        }
        System.out.print("Ingrese el nombre del archivo: ");
        selectedFile = scanner.nextLine();
        Path filePath = selectedFolder.resolve(selectedFile);
        if (!Files.exists(filePath)) {
            System.out.println("Archivo no encontrado.");
            selectedFile = null;
            return;
        }

        // Usar FileHandler correctamente
        data = FileHandler.readFileData(filePath);
    }

    private void convertFile() {
        if (data == null) {
            System.out.println("Debe leer un archivo primero.");
            return;
        }
        System.out.print("Ingrese el formato de salida (csv, json, xml): ");
        String format = scanner.nextLine().toLowerCase();
        System.out.print("Ingrese el nombre del archivo de salida: ");
        Path outputPath = selectedFolder.resolve(scanner.nextLine() + "." + format);

        // Usar FileHandler correctamente
        FileHandler.writeFile(outputPath, data, format);
    }

    private void exitProgram() {
        System.out.println("Saliendo...");
        System.exit(0);
    }
}

