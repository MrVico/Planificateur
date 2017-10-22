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
    private List<AnswerChoice> initialAnswerChoices;

    private Poll poll;
    //inutile j'crois !
    //stocke l'état des réponses du sondage pour un utilisateur pour y accèder lors de la validation
    private Map<Integer, List<Integer>> onLoad = new HashMap<>();


    public void initialize(){
        if (poll != null) {
            title.setText(poll.getTitle());
            place.setText(poll.getLocation());
            description.setText(poll.getDescription());
            initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());
            proposedDates = ListManager.observableListFromList(initialAnswerChoices);
            System.out.println(proposedDates);
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

            List<AnswerChoice> myAnswers = AnswerChoiceController.getInstance().getPollAnswerChoicesForPerson(poll.getID(), User.getUser().getID());
            List<Integer> myAnswersIDs = new ArrayList<>();
            for(AnswerChoice myAnswer : myAnswers){
                myAnswersIDs.add(myAnswer.getID());
                for(AnswerChoice answerChoice : initialAnswerChoices){
                    if(myAnswer.equals(answerChoice)){
                        //TODO : Bizarre de faire des set dans la vue nan ?
                        answerChoice.setCheckProperty(true);
                    }
                }
            }
            onLoad.put(User.getUser().getID(), myAnswersIDs);

            if(PollController.getInstance().getPollPromoterID(poll.getID()) == User.getUser().getID())
                updatePoll.setVisible(true);

            if(poll.getType()!=Poll.pollType.PRIVATE || (poll.getPromoterID()==User.getUser().getID()))
                sharePoll.setVisible(true);

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
        List<Integer> previousAnswersIDs = onLoad.get(User.getUser().getID());
        List<Integer> newAnswersIDs = new ArrayList<>();

        //TODO : Normalement ça ne devrait pas être nécessaire ?!?
        initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());

        //récupération des nouveaux choix de réponses ajoutés par l'utilisateur
        List<AnswerChoice> newAnswerChoices = new ArrayList<>();
        for(AnswerChoice answerChoice : proposedDates){
            if(!initialAnswerChoices.contains(answerChoice)){
                newAnswerChoices.add(new AnswerChoice(null, answerChoice.getCreationDate(), answerChoice.getDateChoice(), poll.getID()));
            }
        }

        //récupèration des réponses sélectionnées par l'utilisateur
        List<Answer> answers = new ArrayList<>();
        for(Object obj : table_dates.getItems()){
            AnswerChoice answerChoice = null;
            if(obj instanceof AnswerChoice) {
                answerChoice = (AnswerChoice) obj;
                if(answerChoice.isCheckProperty()) {
                    //si l'utilisateur a ajouté le choix de réponse il faut d'abord le créé
                    if(newAnswerChoices.contains(answerChoice)) {
                        try {
                            //TODO : Bizarre de faire des set dans la vue nan ?
                            answerChoice.setPollID(poll.getID());
                            if(AnswerChoiceController.getInstance().addAndAnswer(User.getUser().getID(), answerChoice))
                                newAnswerChoices.remove(answerChoice);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    //sinon on ajoute juste la réponse
                    else{
                        newAnswersIDs.add(answerChoice.getID());
                        answers.add(new Answer(User.getUser().getID(), poll.getID(), answerChoice.getID()));
                    }
                }
            }
        }

        //ajout des nouveaux choix de réponses non sélectionnés
        List<AnswerChoice> insertedNewAnswerChoices = new ArrayList<>();
        try {
            insertedNewAnswerChoices = AnswerChoiceController.getInstance().addAll(newAnswerChoices);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //récupèration des réponses désélectionnées par l'utilisateur
        List<Integer> unselectedAnswers = new ArrayList<>();
        for(Integer id : previousAnswersIDs){
            if(!newAnswersIDs.contains(id)){
                unselectedAnswers.add(id);
            }
        }

        //suppression de toutes les réponses désélectionnées par l'utilisateur
        try {
            AnswerController.getInstance().deleteAll(poll.getID(), User.getUser().getID(), unselectedAnswers);
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
