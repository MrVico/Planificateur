package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

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

    protected ObservableList<AnswerChoice> proposedDates;

    protected void initialize() {
        table_dates.setEditable(true);
        TableColumn dateCol = new TableColumn("Date");
        TableColumn hourCol = new TableColumn("Heure");
        dateCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("dateProperty"));

        hourCol.setCellValueFactory(
                new PropertyValueFactory<AnswerChoice, String>("hourProperty"));
        table_dates.getColumns().addAll(dateCol, hourCol);

        table_dates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (table_dates.getSelectionModel().getSelectedItem() != null) {
                remove_date_button.setDisable(false);
            } else {
                remove_date_button.setDisable(true);
            }
        });

        proposed_date.localDateTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (proposed_date.localDateTimeProperty().getValue() != null) {
                proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
                proposed_date.setLocalDateTime(null);
            }
        });
    }

    protected Poll.pollType getPollType() {
        if (radio_public.isSelected())
            return Poll.pollType.PUBLIC;
        else if (radio_private.isSelected())
            return Poll.pollType.PRIVATE;
        else
            return Poll.pollType.PRIVATE_SHARABLE;
    }
}
