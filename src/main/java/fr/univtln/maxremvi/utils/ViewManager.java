package fr.univtln.maxremvi.utils;

import fr.univtln.maxremvi.controller.App;
import fr.univtln.maxremvi.view.ViewControllerInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by remi on 14/10/2017.
 */
public class ViewManager {
    public enum viewsEnum {
        // account
        HOME("home"),
        REGISTER("register"),
        SIGNIN("signIn"),
        // profile
        UPDATE_PROFIL("updateProfil"),
        VIEW_PROFIL("viewProfil"),
        // poll
        CREATE_POLL("createPoll"),
        VIEW_POLL("viewPoll"),
        SHARE_POLL("sharePoll"),
        UPDATE_POLL("updatePoll"),
        RESULTS("results");

        String filename;

        private viewsEnum(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return this.filename;
        }
    }

    ;

    // TODO : Modifier les deux vues
    public static void switchView(viewsEnum viewName, Object... data) {
        String viewUrl = "/views/" + viewName.getFilename() + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));

        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
            ViewControllerInterface controller = fxmlLoader.getController();
            controller.initData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = App.getStage();
        stage.setScene(new Scene(root1, 800, 600));
        stage.setResizable(false);
        stage.show();
    }

    public static void openModal(viewsEnum viewName, Object... data){
        String viewUrl = "/views/" + viewName.getFilename() + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));

        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
            ViewControllerInterface controller = fxmlLoader.getController();
            controller.initData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(new Scene(root1, 600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
