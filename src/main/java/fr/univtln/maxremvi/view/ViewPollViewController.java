package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.ListManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.soap.Text;
import java.util.ArrayList;
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
        proposedDates = ListManager.observableListFromList(AnswerChoiceController.getInstance().getPollAnswerChoices(p.getId()));
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        TableColumn hourCol = new TableColumn("Heure");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("dateProperty"));

        hourCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("hourProperty"));
        table_dates.getColumns().addAll(dateCol, hourCol);

        table_dates.setItems(proposedDates);
    }
}
