package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.utils.ViewUtil;
import javafx.application.Application;
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
        ViewUtil.switchView("signin");
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        App.stage = stage;
    }
}
