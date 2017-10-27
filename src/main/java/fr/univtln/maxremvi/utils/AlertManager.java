package fr.univtln.maxremvi.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertManager {
    /***
     * Creates an alertBox and return it
     *
     * @param type The AlerBox type (
     * @param title The alertBox title (first line)
     * @param header The alertBox header (second line)
     * @param content The alertBox content (third line)
     * @return The alertBox content
     */
    public static Optional<ButtonType> alertBox(AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    /***
     * Creates a sample alertBox for errors
     */
    public static void printError(){
        alertBox(AlertType.ERROR, "Erreur", null, "Une erreur est survenue !");
    }
}
