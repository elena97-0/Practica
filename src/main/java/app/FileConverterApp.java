package app;
import menu.Menu;

/**
 * Clase principal que inicia la aplicación de conversión de archivos.
 */
public class FileConverterApp {
    public static void main(String[] args) {
        // Crea una instancia de la clase Menu
        Menu menu = new Menu();
        // Método que inicia el menú interactivo
        menu.start();
    }
}
