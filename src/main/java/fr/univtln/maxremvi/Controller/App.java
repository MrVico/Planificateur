package fr.univtln.maxremvi.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage stage;

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*
        PersonController personController = new PersonController();
        personController.addPerson("mguil", "mguil", "max", "guil", "max@guil");
        */
        App.setStage(stage);
        Parent root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Planificateur");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        App.stage = stage;
    }
}
