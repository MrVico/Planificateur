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
        CREATE_POLL("createPoll"),
        HOME("home"),
        REGISTER("register"),
        SIGNIN("signin"),
        UPDATE_PROFIL("updateProfil"),
        VIEW_POLL("viewPoll"),
        VIEW_PROFIL("viewProfil"),
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

    // TODO : Modifier les deux vues
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
