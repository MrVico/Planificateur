package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.List;

public class UpdatePollViewController extends FormPollViewController {
    private Poll poll;
    private List<Integer> removedAnswerChoices;

    /**
     * Initializes the UpdatePoll window.
     */
    public void initialize() {
        if (poll != null) {
            super.initialize();
            removedAnswerChoices = new ArrayList();

            title.setText(poll.getTitle());
            location_poll.setText(poll.getLocation());
            description_poll.setText(poll.getDescription());
            addDates.setSelected(poll.isAddDates());
            multipleChoice.setSelected(poll.isMultipleChoice());
            hideAnswers.setSelected(poll.isHideAnswers());

            switch (poll.getType()) {
                case PRIVATE:
                    radio_private.setSelected(true);
                    break;
                case PRIVATE_SHARABLE:
                    radio_private_sharable.setSelected(true);
                    break;
                case PUBLIC:
                    radio_public.setSelected(true);
                    break;
            }

            proposedDates = FXCollections.observableList(AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID()));
            table_dates.setItems(proposedDates);
        }
    }

    /**
     * Handles the actions done on the back button.
     * Switches to the ViewPoll window.
     *
     * @param  event  the type of action that was performed
     */
    public void handleBackButtonClick(ActionEvent event) {
        ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, poll);
    }

    /**
     * Handles the actions done on the save button.
     * Updates the current poll.
     *
     * @param  event  the type of action that was performed
     */
    public void handleSaveButtonClick(ActionEvent event) {
        if (!multipleChoice.isSelected() && PollController.getInstance().getMaxCountAnswer(poll.getID()) > 1) {
            AlertManager.alertBox(Alert.AlertType.ERROR, null, null, "Le sondage ne peut pas être mis à jour car un ou plusieurs utilisateur a sélectionné plus d'une réponse.");
            multipleChoice.setSelected(true);
        }
        else {
            poll.setTitle(title.getText());
            poll.setLocation(location_poll.getText());
            poll.setDescription(description_poll.getText());
            poll.setAddDates(addDates.isSelected());
            poll.setMultipleChoice(multipleChoice.isSelected());
            poll.setHideAnswers(hideAnswers.isSelected());
            poll.setType(getPollType());

            for (AnswerChoice proposedDate : proposedDates) {
                if (proposedDate.getID() == null) {
                    if(AnswerChoiceController.getInstance().addAnswerChoice(proposedDate.getCreationDate(), proposedDate.getDateChoice(), poll.getID()) == null)
                        AlertManager.printError();
                }
            }

            for (Integer answerChoiceId : removedAnswerChoices) {
                if(!AnswerChoiceController.getInstance().delete(answerChoiceId))
                    AlertManager.printError();
            }

            if (PollController.getInstance().updatePoll(poll)) {
                AlertManager.alertBox(Alert.AlertType.INFORMATION, null, null, "Sondage mis à jour avec succès.");
                ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, poll);
            }
            else {
                AlertManager.printError();
            }
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    /**
     * Handles the actions done on the remove date button.
     * Removes the selected date from the poll's answer choices.
     *
     * @param  event  the type of action that was performed
     */
    public void handleRemoveDateButtonClick(ActionEvent event) {
        int focusedIndex = table_dates.getSelectionModel().getFocusedIndex();
        AnswerChoice focusedItem;
        Integer focusedItemId;

        if (focusedIndex != -1) {
            focusedItem = (AnswerChoice) table_dates.getItems().get(focusedIndex);
            focusedItemId = focusedItem.getID();

            if (focusedItemId != null)
                removedAnswerChoices.add(focusedItemId);

            table_dates.getItems().remove(focusedIndex);
        }
    }
}
