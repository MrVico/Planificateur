package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.AnswerController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.*;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import jfxtras.scene.control.LocalDateTimeTextField;

import javax.swing.plaf.ViewportUI;
import javax.swing.text.View;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ViewPollViewController implements ViewControllerInterface {
    @FXML
    private Text title;
    @FXML
    private Text place;
    @FXML
    private Text description;
    @FXML
    private TableView table_dates;
    @FXML
    private Button updatePoll;
    @FXML
    private Button sharePoll;
    @FXML
    private LocalDateTimeTextField proposed_date;
    private ObservableList<AnswerChoice> proposedDates;

    private Poll poll;
    //inutile j'crois !
    //stocke l'état des réponses du sondage pour un utilisateur pour y accèder lors de la validation
    private Map<Integer, List<Integer>> onLoad = new HashMap<>();


    public void initialize(){
        if (poll != null) {
            title.setText(poll.getTitle());
            place.setText(poll.getLocation());
            description.setText(poll.getDescription());
            List<AnswerChoice> answerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getId());
            proposedDates = ListManager.observableListFromList(answerChoices);
            table_dates.setEditable(true);
            TableColumn dateCol = new TableColumn("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("dateProperty"));
            TableColumn hourCol = new TableColumn("Heure");
            hourCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("hourProperty"));
            TableColumn<AnswerChoice, Boolean> checkCol = new TableColumn<>();
            checkCol.setCellValueFactory(new PropertyValueFactory<>("checkProperty"));
            checkCol.setCellFactory(column -> new CheckBoxTableCell());
            checkCol.setSortable(false);
            TableColumn answerCol = new TableColumn();
            answerCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("timesChosenProperty"));

            table_dates.getColumns().add(dateCol);
            table_dates.getColumns().add(hourCol);
            table_dates.getColumns().add(checkCol);
            table_dates.getColumns().add(answerCol);
            table_dates.setItems(proposedDates);

            List<AnswerChoice> myAnswers = AnswerChoiceController.getInstance().getPollAnswerChoicesForPerson(poll.getId(), User.getUser().getId());
            List<Integer> myAnswersIDs = new ArrayList<>();
            for(AnswerChoice myAnswer : myAnswers){
                myAnswersIDs.add(myAnswer.getId());
                for(AnswerChoice answerChoice : answerChoices){
                    if(myAnswer.equals(answerChoice)){
                        //TODO : A changer, passer par le controleur etc ???
                        answerChoice.setCheckProperty(true);
                    }
                }
            }
            onLoad.put(User.getUser().getId(), myAnswersIDs);

            if(PollController.getInstance().getPollPromoterID(poll.getId()) == User.getUser().getId())
                updatePoll.setVisible(true);
            //TODO : sharePoll button only visible if public or private + sharable.

            proposed_date.localDateTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (proposed_date.localDateTimeProperty().getValue() != null) {
                    proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
                    proposed_date.setLocalDateTime(null);
                }
            });
        }
    }

    @FXML
    public void handleValidateAnswerButtonClick(ActionEvent actionEvent) {
        List<Integer> previousAnswersIDs = onLoad.get(User.getUser().getId());
        List<Integer> newAnswersIDs = new ArrayList<>();

        //récupèration des réponses sélectionnées par l'utilisateur
        List<Answer> answers = new ArrayList<>();
        for(Object obj : table_dates.getItems()){
            AnswerChoice answerChoice = null;
            if(obj instanceof AnswerChoice) {
                answerChoice = (AnswerChoice) obj;
                if(answerChoice.isCheckProperty()) {
                    newAnswersIDs.add(answerChoice.getId());
                    answers.add(new Answer(User.getUser().getId(), poll.getId(), answerChoice.getId()));
                }
            }
        }

        //récupèration des réponses désélectionnées par l'utilisateur
        List<Integer> unselectedAnswers = new ArrayList<>();
        for(Integer id : previousAnswersIDs){
            if(!newAnswersIDs.contains(id)){
                unselectedAnswers.add(id);
            }
        }

        try {
            System.out.println("Unselected answers : "+unselectedAnswers);
            AnswerController.getInstance().deleteAll(poll.getId(), User.getUser().getId(), unselectedAnswers);
            System.out.println("New answers : "+answers);
            AnswerController.getInstance().addAll(answers);
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Merci de votre participation.");
            ViewManager.switchView(ViewManager.viewsEnum.HOME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    public void handleUpdatePollButtonClick(ActionEvent actionEvent) {
        AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "TODO !!!");
    }

    public void handleSharePollButtonClick(ActionEvent actionEvent) {
        ViewManager.openModal(ViewManager.viewsEnum.SHARE_POLL, poll);
    }
}
