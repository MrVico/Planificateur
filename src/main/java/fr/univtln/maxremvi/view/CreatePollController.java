package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.database.PersonDao;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeUtil;
import fr.univtln.maxremvi.utils.ViewUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.*;

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
    private LocalDateTimeTextField end_date;
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

        pollController = PollController.getInstance();
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

            Date endDate = TimeUtil.localDateToDate(end_date.getLocalDateTime().toLocalDate());

            PersonDao personDao = new PersonDao();
            Person promoter = personDao.get(1);

            try {
                pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), endDate, false, promoter);
                ViewUtil.switchView("home");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
