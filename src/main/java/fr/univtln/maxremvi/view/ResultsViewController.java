package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.text.SimpleDateFormat;
import java.util.*;

public class ResultsViewController implements ViewControllerInterface {
    @FXML
    private Label dateFinale;
    @FXML
    private Button notifyButton;
    @FXML
    private Label noAnswer;
    @FXML
    private PieChart graphic;
    @FXML
    private ComboBox<AnswerChoice> cbbChoixFinal;
    private Poll poll;
    private List<AnswerChoice> answerChoices;

    /**
     * Initializes the ResultsView window.
     */
    public void initialize(){
        if(poll != null){
            answerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());

            //order the list from the most chosen answers to the less chosen one
            Collections.sort(answerChoices, new Comparator<AnswerChoice>() {
                @Override
                public int compare(AnswerChoice o1, AnswerChoice o2) {
                    return Integer.parseInt(o2.getTimesChosenProperty().substring(1,o2.getTimesChosenProperty().length()-1)) - Integer.parseInt(o1.getTimesChosenProperty().substring(1,o1.getTimesChosenProperty().length()-1));
                }
            });

            List<PieChart.Data> data = new ArrayList<>();

            Iterator<AnswerChoice> i = answerChoices.iterator();
            while (i.hasNext()) {
                AnswerChoice answerChoice = i.next();
                int timesChosen = Integer.parseInt(answerChoice.getTimesChosenProperty().substring(1,answerChoice.getTimesChosenProperty().length()-1));
                if(timesChosen>0)
                    data.add(new PieChart.Data(TimeManager.format("dd/MM/yyyy HH:mm", answerChoice.getDateChoice()), timesChosen));
                //this answer choice didn't get any votes. It's removed from the possible solution list
                else
                    i.remove();
            }

            if(data.size() > 0){
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
                graphic.setData(pieChartData);
                graphic.setTitle("Résultat du sondage");
                graphic.setLabelsVisible(true);
                graphic.setLegendVisible(false);

                cbbChoixFinal.setItems(FXCollections.observableArrayList(answerChoices));
                cbbChoixFinal.getSelectionModel().select(0);
            }
            else {
                notifyButton.setVisible(false);
                graphic.setVisible(false);
                dateFinale.setVisible(false);
                cbbChoixFinal.setVisible(false);
                noAnswer.setVisible(true);
            }
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    /**
     * Handles the actions done on the return home button.
     * Switches to the Home window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleReturnHomeButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    /**
     * Handles the actions done on the notify button.
     * Notifies all participants of the final date.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleNotifyButtonClick(ActionEvent actionEvent) {
        if(cbbChoixFinal.getSelectionModel().getSelectedIndex()!=-1){
            AnswerChoice choixFinal = cbbChoixFinal.getSelectionModel().getSelectedItem();
            if(PollController.getInstance().setFinalDate(poll.getID(), choixFinal.getDateChoice())){
                AlertManager.alertBox(Alert.AlertType.INFORMATION, "Information", null, "Tous les participants vont être notifier de votre choix.");
                ViewManager.switchView(ViewManager.viewsEnum.HOME);
            }
            else{
                AlertManager.printError();
            }
        }
        else
            AlertManager.alertBox(Alert.AlertType.INFORMATION, "Information",null, "Veuillez choisir une date de réunion.");
    }
}
