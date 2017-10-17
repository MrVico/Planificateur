package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.database.PersonDao;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.model.PollDate;
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
import java.util.Date;

/**
 * Created by remi on 14/10/2017.
 */
public class CreatePollController {
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

    private ObservableList<PollDate> proposedDates;

    public void initialize() {
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        TableColumn hourCol = new TableColumn("Heure");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<PollDate, String>("dateProperty"));

        hourCol.setCellValueFactory(
                new PropertyValueFactory<PollDate, String>("hourProperty"));
        table_dates.getColumns().addAll(dateCol, hourCol);

        pollController = PollController.getInstance();
        proposedDates = ListManager.observableListFromList(new ArrayList<PollDate>());
        table_dates.setItems(proposedDates);
    }

    public void handleCreatePollButton(ActionEvent event) {
        Poll.type pollType = null;

        if (title.getText().isEmpty() || location_poll.getText().isEmpty() || description_poll.getText().isEmpty() || end_date.getText().isEmpty()) {
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
            Person promoter = personDao.get(1);

            try {
                Poll savedPoll = pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), endDate, false, promoter);
                ViewManager.switchView("home");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleAddDate(ActionEvent event) {
        if (!proposed_date.getText().isEmpty()) {
            proposedDates.add(new PollDate(TimeManager.localDateToDate(proposed_date.getLocalDateTime())));
            proposed_date.setText("");
        }
    }
}
