package fr.univtln.maxremvi.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertManager {
    /***
     * Creates an AlertBox and return it
     *
     * @param type The AlerBox type (
     * @param title The AlertBox title (first line)
     * @param header The AlertBox header (second line)
     * @param content The AlertBox content (third line)
     * @return The AlertBox content
     */
    public static Optional<ButtonType> AlertBox(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    /***
     * Creates a sample AlertBox for errors
     */
    public static void printError(){
        AlertBox(AlertType.ERROR, "Erreur", null, "Une erreur est survenue !");
    }
}
