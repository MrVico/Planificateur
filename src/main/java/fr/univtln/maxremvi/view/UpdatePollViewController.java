package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import jfxtras.scene.control.LocalDateTimeTextField;

import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Created by remi on 24/10/2017.
 */
public class UpdatePollViewController extends FormPollViewController {
    private Poll poll;

    public void initialize() {
        if (poll != null) {
            title.setText(poll.getTitle());
            location_poll.setText(poll.getLocation());
            description_poll.setText(poll.getDescription());
            addDates.setSelected(poll.isAddDates());
            multipleChoice.setSelected(poll.isMultipleChoice());
            hideAnswers.setSelected(poll.isHideAnswers());
            end_date.setLocalDateTime(TimeManager.dateToLocalDateTime(poll.getClosingDate()));
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
        }
    }

    public void handleBackButtonClick(ActionEvent event) {

    }

    public void handleSaveButtonClick(ActionEvent event) {
        poll.setTitle(title.getText());
        poll.setLocation(location_poll.getText());
        poll.setDescription(description_poll.getText());
        poll.setAddDates(addDates.isSelected());
        poll.setMultipleChoice(multipleChoice.isSelected());
        poll.setHideAnswers(hideAnswers.isSelected());
        poll.setClosingDate(getEndDate());
        poll.setType(getPollType());

        // TODO : Mettre à jour les choix de réponse

        try {
            if (PollController.getInstance().updatePoll(poll)) {
                AlertManager.AlertBox(Alert.AlertType.INFORMATION, null, null, "Sondage mis à jour avec succès.");
                ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, poll);
            }
            else {
                AlertManager.AlertBox(Alert.AlertType.ERROR, null, null, "Erreur lors de la mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }
}
