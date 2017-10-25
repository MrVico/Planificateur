package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.AnswerChoiceController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by remi on 24/10/2017.
 */
public class UpdatePollViewController extends FormPollViewController {
    private Poll poll;
    private List<Integer> removedAnswerChoices;

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

            proposedDates = ListManager.observableListFromList(AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID()));
            table_dates.setItems(proposedDates);
        }
    }

    public void handleBackButtonClick(ActionEvent event) {
        ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, poll);
    }

    public void handleSaveButtonClick(ActionEvent event) {
        if (!multipleChoice.isSelected() && PollController.getInstance().getMaxCountAnswer(poll.getID()) > 1) {
            AlertManager.AlertBox(Alert.AlertType.ERROR, null, null, "Le sondage ne peut pas être mis à jour car un ou plusieurs utilisateur a sélectionné plus d'une réponse.");
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
                    try {
                        AnswerChoiceController.getInstance().addAnswerChoice(proposedDate.getCreationDate(), proposedDate.getDateChoice(), poll.getID());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (Integer answerChoiceId : removedAnswerChoices) {
                try {
                    AnswerChoiceController.getInstance().delete(answerChoiceId);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            try {
                if (PollController.getInstance().updatePoll(poll)) {
                    AlertManager.AlertBox(Alert.AlertType.INFORMATION, null, null, "Sondage mis à jour avec succès.");
                    ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, poll);
                } else {
                    AlertManager.AlertBox(Alert.AlertType.ERROR, null, null, "Erreur lors de la mise à jour.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

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
