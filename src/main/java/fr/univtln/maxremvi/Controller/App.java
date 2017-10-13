package fr.univtln.maxremvi.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //PersonController personController=new PersonController();
       // personController.addPerson("mguil","mguil","max","guil","max@guil");
        Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("Planificateur");
        stage.setScene(scene);
        stage.show();
    }
}
