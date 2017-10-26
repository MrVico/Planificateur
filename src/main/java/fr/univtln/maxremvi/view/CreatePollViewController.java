package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import java.util.ArrayList;

public class CreatePollViewController extends FormPollViewController {
    private PollController pollController;
    private AnswerChoiceController answerChoiceController;

    /**
     * Initializes the CreatePoll window.
     */
    public void initialize() {
        super.initialize();

        proposedDates = ListManager.observableListFromList(new ArrayList<AnswerChoice>());
        table_dates.setItems(proposedDates);

        pollController = PollController.getInstance();
        answerChoiceController = AnswerChoiceController.getInstance();

        description_poll.setWrapText(true);
    }

    /**
     * Handles the actions done on the create poll button.
     * Creates the poll if everything is fill in correctly.
     *
     * @param  event  the type of action that was performed
     */
    public void handleCreatePollButtonClick(ActionEvent event) {
        if (title.getText().isEmpty() || location_poll.getText().isEmpty() || description_poll.getText().isEmpty() || proposedDates.isEmpty()) {
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Tous les champs sont obligatoires.");
        }
        else {
            Poll savedPoll = pollController.addPoll(title.getText(), description_poll.getText(), location_poll.getText(), false, User.getUser().getID(), multipleChoice.isSelected(), hideAnswers.isSelected(), addDates.isSelected(), getPollType());
            if (proposedDates != null) {
                proposedDates.forEach(answerChoice -> answerChoice.setPollID(savedPoll.getID()));
                if(answerChoiceController.addAll(proposedDates) == null)
                    AlertManager.printError();
            }
            ViewManager.switchView(ViewManager.viewsEnum.HOME);
        }

    }

    /**
     * Handles the actions done on the remove date button.
     * Removes the selected date from the answer choices.
     *
     * @param  event  the type of action that was performed
     */
    public void handleRemoveDateButtonClick(ActionEvent event) {
        int focusedIndex = table_dates.getSelectionModel().getFocusedIndex();
        if (focusedIndex != -1)
            table_dates.getItems().remove(focusedIndex);
    }

    /**
     * Handles the actions done on the back button.
     * Switches to the Home window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleBackButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    @Override
    public void initData(Object... arguments) {

    }
}
