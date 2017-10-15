package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.PollController;
import fr.univtln.maxremvi.Model.Poll;
import fr.univtln.maxremvi.Utils.AlertManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.CalendarPicker;
import jfxtras.scene.control.CalendarTextField;

import java.io.IOException;
import java.sql.SQLException;
import java.time.ZoneId;
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
    private CalendarTextField end_date;
    @FXML
    private RadioButton radio_public;
    @FXML
    private RadioButton radio_private_sharable;
    @FXML
    private RadioButton radio_private;
    @FXML
    private TableView table_dates;

    private PollController pollController;

    public void initialize() {
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        TableColumn hourCol = new TableColumn("Heure");
        table_dates.getColumns().addAll(dateCol, hourCol);

        pollController = new PollController();
    }

    public void handleCreatePollButton(ActionEvent event) {
        Poll.type pollType = null;

        if (title.getText().isEmpty() || location_poll.getText().isEmpty() || description_poll.getText().isEmpty() || end_date.getCalendar() != null) {
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Les champs en 'IDENTIFICATEUR' doivent obligatoirement être renseignés.");
        } else {
            if (radio_public.isSelected())
                pollType = Poll.type.PUBLIC;
            else if (radio_private.isSelected())
                pollType = Poll.type.PRIVATE;
            else
                pollType = Poll.type.PRIVATE_SHARABLE;

            Date endDate = end_date.getCalendar().getTime();
            try {
                pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), endDate, false, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
