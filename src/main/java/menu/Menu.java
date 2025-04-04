package menu;

import handlers.FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

///Clase que representa un menú interactivo para gestionar archivos.
///Permite seleccionar una carpeta, leer un archivo, convertir su formato y salir del programa
public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private Path selectedFolder;
    private List<Map<String, String>> data;
    private String selectedFile;

    ///Inicia el menú en un bucle infinito hasta que el usuario elija salir.
    public void start() {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            handleMenuChoice(choice);
        }
    }

    ///Muestra el menú de opciones al usuario
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

    /// Maneja la opción seleccionada por el usuario en el menú.
    /// @param choice Opción elegida por el usuario.
    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> selectFolder();
            case 2 -> readFile();
            case 3 -> convertFile();
            case 4 -> exitProgram();
            default -> System.out.println("Opción no válida.");
        }
    }

    ///Permite al usuario ingresar la ruta de una carpeta y la establece como la carpeta seleccionada
    private void selectFolder() {
        System.out.print("Ingrese la ruta de la carpeta: ");
        Path path = Paths.get(scanner.nextLine());
        if (Files.isDirectory(path)) {
            selectedFolder = path;
        } else {
            System.out.println("Carpeta no válida.");
        }
    }

    /// Lista los archivos dentro de la carpeta seleccionada.
    /// @param folder Carpeta de la que se listarán los archivos.
    private void listFiles(Path folder) {
        try {
            Files.list(folder).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Error al listar archivos.");
        }
    }

    ///Permite al usuario seleccionar y leer un archivo dentro de la carpeta elegida.
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

        
        data = FileHandler.readFileData(filePath);
    }

    ///Convierte el archivo seleccionado a un formato diferente
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

    ///Finaliza la ejecución del programa.
    private void exitProgram() {
        System.out.println("Saliendo...");
        System.exit(0);
    }
}

