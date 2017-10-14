package fr.univtln.maxremvi.Model;

import fr.univtln.maxremvi.Controller.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by remi on 14/10/2017.
 */
public class ViewUtil {
    public static void switchView(String viewName) throws java.io.IOException {
        String viewUrl = "/views/" + viewName + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = App.getStage();
        stage.setScene(new Scene(root1, 800, 600));
        stage.show();
    }
}
