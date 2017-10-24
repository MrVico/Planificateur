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

    public void initialize() {
        super.initialize();

        proposedDates = ListManager.observableListFromList(new ArrayList<AnswerChoice>());
        table_dates.setItems(proposedDates);

        pollController = PollController.getInstance();
        answerChoiceController = AnswerChoiceController.getInstance();

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
