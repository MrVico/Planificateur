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
    private static int VIEW_WIDTH = 800;
    private static int VIEW_HEIGHT = 600;
    private static int MODAL_WIDTH = 600;
    private static int MODAL_HEIGHT = 400;

    // The views of the project
    public enum viewsEnum {
        // Account
        HOME("home"),
        REGISTER("register"),
        SIGNIN("signIn"),
        // Profile
        UPDATE_PROFIL("updateProfil"),
        VIEW_PROFIL("viewProfil"),
        // Poll
        CREATE_POLL("createPoll"),
        VIEW_POLL("viewPoll"),
        SHARE_POLL("sharePoll"),
        UPDATE_POLL("updatePoll"),
        RESULTS("results");

        String filename;

        public String getFilename() {
            return this.filename;
        }

        private viewsEnum(String filename) {
            this.filename = filename;
        }
    }

    /***
     * Gets the view path of the given view name
     *
     * @param viewName The view name (stored in ViewManager.viewsEnum)
     * @return The view path
     */
    private static String getViewPath(viewsEnum viewName) {
        return "/views/" + viewName.getFilename() + ".fxml";
    }

    /***
     * Switches to the given view and passes it arguments
     *
     * @param viewName The view to switch to
     * @param data The arguments to pass to the view
     */
    public static void switchView(viewsEnum viewName, Object... data) {
        String viewUrl = getViewPath(viewName);
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));

        Parent root1 = loadView(fxmlLoader, data);

        Stage stage = App.getStage();
        stage.setScene(new Scene(root1, VIEW_WIDTH, VIEW_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    /***
     * Opens a modal containing the given view and passes it arguments
     *
     * @param viewName The view to open in modal
     * @param data The arguments to pass to the view
     */
    public static void openModal(viewsEnum viewName, Object... data){
        String viewUrl = getViewPath(viewName);
        FXMLLoader fxmlLoader = new FXMLLoader(viewUrl.getClass().getResource(viewUrl));

        Parent root1 = loadView(fxmlLoader, data);

        Stage stage = new Stage();
        stage.setScene(new Scene(root1, MODAL_WIDTH, MODAL_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    /***
     * Loads the root node of the view and passes arguments to the controller
     *
     * @param fxmlLoader The FXMLLoader of the view
     * @param data The data to pass to the view
     * @return The root node of the view
     */
    private static Parent loadView(FXMLLoader fxmlLoader, Object[] data) {
        Parent root1 = null;

        try {
            root1 = (Parent) fxmlLoader.load();
            ViewControllerInterface controller = fxmlLoader.getController();
            controller.initData(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root1;
    }
}
