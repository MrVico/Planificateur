package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultsViewController implements ViewControllerInterface {
    @FXML
    private Label title;
    @FXML
    private Label nbParticipants;
    private Poll poll;

    public void initialize(){
        if(poll != null){
            title.setText("Résultats du sondage "+poll.getTitle());
            nbParticipants.setText("Nombre de participants : 5");
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    public void handleReturnHomeButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }
}
