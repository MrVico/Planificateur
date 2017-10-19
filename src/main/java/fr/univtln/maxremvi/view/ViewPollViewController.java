package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.AnswerController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.*;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewPollViewController implements ViewControllerInterface {
    @FXML
    private TextField title;
    @FXML
    private TextField place;
    @FXML
    private TextArea description;
    @FXML
    private TableView table_dates;
    private ObservableList<AnswerChoice> proposedDates;

    private Poll poll;


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
            for(AnswerChoice myAnswer : myAnswers){
                for(AnswerChoice answerChoice : answerChoices){
                    if(myAnswer.equals(answerChoice)){
                        //TODO : A changer, passer par le controleur etc ???
                        answerChoice.setCheckProperty(true);
                    }
                }
            }
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
        List<AnswerChoice> answerChoices = new ArrayList<>();
        for(Object obj : table_dates.getItems()){
            AnswerChoice answerChoice = null;
            if(obj instanceof AnswerChoice) {
                answerChoice = (AnswerChoice) obj;
                if(answerChoice.isCheckProperty())
                    answerChoices.add(answerChoice);
            }
        }
        try {
            AnswerController.getInstance().addAnswer(User.getUser().getId(), poll.getId(), answerChoices);
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Merci de votre participation.");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }
}
