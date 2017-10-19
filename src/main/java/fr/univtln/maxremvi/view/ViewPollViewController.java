package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.AnswerController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.*;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.swing.plaf.ViewportUI;
import javax.swing.text.View;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private ObservableList<AnswerChoice> proposedDates;

    private Poll poll;
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

            table_dates.getColumns().add(dateCol);
            table_dates.getColumns().add(hourCol);
            table_dates.getColumns().add(checkCol);
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
        }

        // NE PAS SUPPRIMER
        /*
        List<AnswerChoiceFormatted> answerChoiceFormatteds = new ArrayList<>();

        ObservableList<SimpleStringProperty> hours_1 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(0).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_1 = new SimpleListProperty<>(hours_1);
        AnswerChoiceFormatted answerChoiceFormatted_1 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(0).getDateProperty()), hours2_1);

        ObservableList<SimpleStringProperty> hours_2 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(1).getHourProperty()), new SimpleStringProperty(answerChoices.get(2).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_2 = new SimpleListProperty<>(hours_2);
        AnswerChoiceFormatted answerChoiceFormatted_2 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(1).getDateProperty()), hours2_2);

        ObservableList<SimpleStringProperty> hours_3 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(3).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_3 = new SimpleListProperty<>(hours_3);
        AnswerChoiceFormatted answerChoiceFormatted_3 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(3).getDateProperty()), hours2_3);

        answerChoiceFormatteds.add(answerChoiceFormatted_1);
        answerChoiceFormatteds.add(answerChoiceFormatted_2);
        answerChoiceFormatteds.add(answerChoiceFormatted_3);

        System.out.println(answerChoiceFormatteds);
        ObservableList<AnswerChoiceFormatted> list = ListManager.observableListFromList(answerChoiceFormatteds);

        List<TableColumn> hourCols = new ArrayList<>();
        int highestAmountOfHoursPerDay = 0;
        for(int i=0; i<answerChoices.size(); i++){
            String currentDate = TimeManager.extractDateString(answerChoices.get(i).getDateChoice());
            int j = i;
            int count = 0;
            while(j<answerChoices.size() && TimeManager.extractDateString(answerChoices.get(j).getDateChoice()).equals(currentDate)){
                j++;
                count++;
            }
            if(highestAmountOfHoursPerDay<count)
                highestAmountOfHoursPerDay = count;
            i = j-1;
            System.out.println(currentDate+" "+count);
        }
        System.out.println(highestAmountOfHoursPerDay);

        for(int i=0; i<highestAmountOfHoursPerDay; i++){
            TableColumn hourCol = new TableColumn("Heure "+(i+1));
            hourCol.setCellValueFactory(new PropertyValueFactory<AnswerChoiceFormatted, String>("hourProperties"));
            hourCols.add(hourCol);
        }
        */
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
                    answers.add(new Answer(poll.getId(), User.getUser().getId(), answerChoice.getId()));
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
}
