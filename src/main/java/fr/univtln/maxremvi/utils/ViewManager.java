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
        SIGNIN("signin"),
        // profile
        UPDATE_PROFIL("updateProfil"),
        VIEW_PROFIL("viewProfil"),
        // poll
        CREATE_POLL("createPoll"),
        VIEW_POLL("viewPoll"),
        SHARE_POLL("sharePoll");

        String filename;

        private viewsEnum(String filename) {
            this.filename = filename;
        }

        public String getFilename() {
            return this.filename;
        }
    }

    ;

    public static void switchView(viewsEnum viewName, Object... data) {
        /*
        if(viewName != "signin" && viewName != "register" && !User.isLogged()){
            ViewManager.switchView("signin");
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Utilisateur non identifié", null, "Veuillez vous connectez !");
        }
        else{*/
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
        //}
    }

    //TODO : Superposition !
    public static void openModal(viewsEnum viewName){
        String viewUrl = "/views/" + viewName.getFilename() + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));

        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = App.getStage();
        stage.setScene(new Scene(root1, 600, 400));
        stage.setResizable(false);
        stage.show();
    }
}
