package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.database.PersonDao;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jfxtras.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

/**
 * Created by remi on 14/10/2017.
 */
public class CreatePollViewController {
    @FXML
    private TextField title;
    @FXML
    private TextField location_poll;
    @FXML
    private TextArea description_poll;
    @FXML
    private CheckBox possibility_add_dates_checkbox;
    @FXML
    private LocalDateTimeTextField end_date;
    @FXML
    private LocalDateTimeTextField proposed_date;
    @FXML
    private RadioButton radio_public;
    @FXML
    private RadioButton radio_private_sharable;
    @FXML
    private RadioButton radio_private;
    @FXML
    private TableView table_dates;

    private PollController pollController;
    private AnswerChoiceController answerChoiceController;

    private ObservableList<AnswerChoice> proposedDates;

    public void initialize() {
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        TableColumn hourCol = new TableColumn("Heure");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("dateProperty"));

        hourCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("hourProperty"));
        table_dates.getColumns().addAll(dateCol, hourCol);

        pollController = PollController.getInstance();
        answerChoiceController = AnswerChoiceController.getInstance();

        proposedDates = ListManager.observableListFromList(new ArrayList<AnswerChoice>());
        table_dates.setItems(proposedDates);
    }

    public void handleCreatePollButton(ActionEvent event) {
        Poll.type pollType = null;

        if (title.getText().isEmpty() || location_poll.getText().isEmpty() || description_poll.getText().isEmpty() || end_date.getText().isEmpty() || proposedDates.isEmpty()) {
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Les champs en 'IDENTIFICATEUR' doivent obligatoirement être renseignés.");
        } else {
            if (radio_public.isSelected())
                pollType = Poll.type.PUBLIC;
            else if (radio_private.isSelected())
                pollType = Poll.type.PRIVATE;
            else
                pollType = Poll.type.PRIVATE_SHARABLE;

            Date endDate = TimeManager.localDateToDate(end_date.getLocalDateTime());

            PersonDao personDao = new PersonDao();
            Person promoter = User.getUser();

            try {
                Poll savedPoll = pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), endDate, false, promoter);

                if (proposedDates != null) {
                    proposedDates.forEach(answerChoice -> answerChoice.setIdPoll(savedPoll.getId()));
                    answerChoiceController.addAll(proposedDates);
                }

                ViewManager.switchView("home");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleAddDate(ActionEvent event) {
        if (!proposed_date.getText().isEmpty()) {
            proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
            proposed_date.setText("");
        }
    }
}
