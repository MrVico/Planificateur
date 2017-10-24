package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by remi on 14/10/2017.
 */
public class CreatePollViewController extends FormPollViewController {
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

        table_dates.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (table_dates.getSelectionModel().getSelectedItem() != null) {
                    remove_date_button.setDisable(false);
                } else {
                    remove_date_button.setDisable(true);
                }
            }
        });

        proposed_date.localDateTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (proposed_date.localDateTimeProperty().getValue() != null) {
                proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
                proposed_date.setLocalDateTime(null);
            }
        });

        description_poll.setWrapText(true);
    }

    public void handleCreatePollButtonClick(ActionEvent event) {
        if (title.getText().isEmpty() || location_poll.getText().isEmpty() || description_poll.getText().isEmpty() || proposedDates.isEmpty()) {
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Les champs en 'IDENTIFICATEUR' doivent obligatoirement être renseignés.");
        } else {
            try {
                Poll savedPoll = pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), getEndDate(), false, User.getUser().getID(), multipleChoice.isSelected(), hideAnswers.isSelected(), addDates.isSelected(), getPollType());

                if (proposedDates != null) {
                    proposedDates.forEach(answerChoice -> answerChoice.setPollID(savedPoll.getID()));
                    answerChoiceController.addAll(proposedDates);
                }

                ViewManager.switchView(ViewManager.viewsEnum.HOME);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public void handleRemoveDateButtonClick(ActionEvent event) {
        int focusedIndex = table_dates.getSelectionModel().getFocusedIndex();
        if (focusedIndex != -1)
            table_dates.getItems().remove(focusedIndex);
    }

    public void handleBackButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    @Override
    public void initData(Object... arguments) {

    }
}
