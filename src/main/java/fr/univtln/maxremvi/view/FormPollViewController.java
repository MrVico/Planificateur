package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.TimeManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.util.Date;

/**
 * Created by remi on 24/10/2017.
 */
public abstract class FormPollViewController implements ViewControllerInterface {
    @FXML
    protected TextField title;
    @FXML
    protected TextField location_poll;
    @FXML
    protected TextArea description_poll;
    @FXML
    protected CheckBox addDates;
    @FXML
    protected CheckBox multipleChoice;
    @FXML
    protected CheckBox hideAnswers;
    @FXML
    protected LocalDateTimeTextField end_date;
    @FXML
    protected LocalDateTimeTextField proposed_date;
    @FXML
    protected RadioButton radio_public;
    @FXML
    protected RadioButton radio_private_sharable;
    @FXML
    protected RadioButton radio_private;
    @FXML
    protected TableView table_dates;
    @FXML
    protected Button remove_date_button;


    protected Poll.pollType getPollType() {
        if (radio_public.isSelected())
            return Poll.pollType.PUBLIC;
        else if (radio_private.isSelected())
            return Poll.pollType.PRIVATE;
        else
            return Poll.pollType.PRIVATE_SHARABLE;
    }

    protected Date getEndDate() {
        return (end_date.getLocalDateTime() != null) ? TimeManager.localDateToDate(end_date.getLocalDateTime()) : null;
    }
}
