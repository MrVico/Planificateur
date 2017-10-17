package fr.univtln.maxremvi.utils;

import fr.univtln.maxremvi.controller.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by remi on 14/10/2017.
 */
public class ViewManager {
    public static void switchView(String viewName){
        String viewUrl = "/views/" + viewName + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = App.getStage();
        stage.setScene(new Scene(root1, 800, 600));
        stage.setResizable(false);
        stage.show();
    }
}
