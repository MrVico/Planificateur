package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.AnswerChoiceFormatted;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.soap.Text;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPollViewController {
    @FXML
    private TextField title;
    @FXML
    private TextField place;
    @FXML
    private TextArea description;
    @FXML
    private TableView<AnswerChoice> pollDateTable;
    @FXML
    private TableView table_dates;
    private ObservableList<AnswerChoice> proposedDates;


    public void initialize(){
        Poll p = PollController.getInstance().getPoll(666);
        title.setText(p.getTitle());
        place.setText(p.getLocation());
        description.setText(p.getDescription());
        List<AnswerChoice> answerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(p.getId());
        proposedDates = ListManager.observableListFromList(answerChoices);
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<AnswerChoiceFormatted, String>("dateProperty"));


        List<AnswerChoiceFormatted> answerChoiceFormatteds = new ArrayList<>();

        ObservableList<SimpleStringProperty> hours_1 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(0).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_1 = new SimpleListProperty<>(hours_1);
        AnswerChoiceFormatted answerChoiceFormatted_1 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(0).getDateProperty()), hours2_1);

        ObservableList<SimpleStringProperty> hours_2 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(1).getHourProperty()), new SimpleStringProperty(answerChoices.get(2).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_2 = new SimpleListProperty<>(hours_2);
        AnswerChoiceFormatted answerChoiceFormatted_2 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(1).getDateProperty()), hours2_2);

        ObservableList<SimpleStringProperty> hours_3 = ListManager.observableListFromList(Arrays.asList(new SimpleStringProperty(answerChoices.get(2).getHourProperty())));
        ListProperty<SimpleStringProperty> hours2_3 = new SimpleListProperty<>(hours_3);
        AnswerChoiceFormatted answerChoiceFormatted_3 = new AnswerChoiceFormatted(new SimpleStringProperty(answerChoices.get(2).getDateProperty()), hours2_3);

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
            hourCol.setCellValueFactory(new PropertyValueFactory<AnswerChoiceFormatted, String>("hourProperty"));
            hourCols.add(hourCol);
        }

        table_dates.getColumns().add(dateCol);
        table_dates.getColumns().addAll(hourCols);
        table_dates.setItems(list);
    }
}
