package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.*;
import fr.univtln.maxremvi.model.*;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jfxtras.scene.control.LocalDateTimeTextField;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ViewPollViewController implements ViewControllerInterface {
    @FXML
    private Text title;
    @FXML
    private Text place;
    @FXML
    private Text description;
    @FXML
    private Text type;
    @FXML
    private Text choice;
    @FXML
    private TableView table_dates;
    @FXML
    private Button updatePoll;
    @FXML
    private Button sharePoll;
    @FXML
    private Button deletePoll;
    @FXML
    private Button closePoll;
    @FXML
    private Button updateChat;
    @FXML
    private LocalDateTimeTextField proposed_date;
    @FXML
    private TextArea message;
    @FXML
    private ListView chat;
    private final ObservableList<VBox> lines = FXCollections.observableArrayList();
    private List<Message> messages;
    private ObservableList<AnswerChoice> proposedDates;
    private List<AnswerChoice> initialAnswerChoices;

    private Poll poll;
    private Map<Integer, List<Integer>> onLoad = new HashMap<>();

    /**
     * Initializes the ViewPoll window.
     */
    public void initialize() {
        if (poll != null) {
            pollInvitationSeen();
            title.setText(poll.getTitle());
            place.setText(poll.getLocation());
            description.setText(poll.getDescription());
            if(poll.getType().toString().equals("PUBLIC"))
                type.setText("Public");
            else if(poll.getType().toString().equals("PRIVATE"))
                type.setText("Privée");
            else
                type.setText("Privée partageable");
            if(poll.isMultipleChoice())
                choice.setText("multiple");
            else
                choice.setText("unique");
            initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());
            proposedDates = FXCollections.observableList(initialAnswerChoices);
            table_dates.setEditable(true);
            TableColumn dateCol = new TableColumn("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("dateProperty"));
            TableColumn hourCol = new TableColumn("Heure");
            hourCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("hourProperty"));


            table_dates.getColumns().add(dateCol);
            table_dates.getColumns().add(hourCol);


            if (!poll.isMultipleChoice()) {

                table_dates.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            } else {
                table_dates.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                TableColumn<AnswerChoice, Boolean> checkCol = new TableColumn<>();
                checkCol.setCellValueFactory(new PropertyValueFactory<>("checkProperty"));
                checkCol.setCellFactory(column -> new CheckBoxTableCell<>());
                checkCol.setSortable(false);
                table_dates.getColumns().add(checkCol);
            }

            if (!poll.isHideAnswers()) {
                TableColumn answerCol = new TableColumn();
                answerCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("timesChosenProperty"));
                table_dates.getColumns().add(answerCol);
            }

            table_dates.setItems(proposedDates);

            if (!poll.isAddDates())
                proposed_date.setDisable(true);

            List<AnswerChoice> myAnswers = AnswerChoiceController.getInstance().getPollAnswerChoicesForPerson(poll.getID(), User.getUser().getID());
            List<Integer> myAnswersIDs = new ArrayList<>();
            for (AnswerChoice myAnswer : myAnswers) {
                myAnswersIDs.add(myAnswer.getID());

                for (AnswerChoice answerChoice : initialAnswerChoices) {
                    if (myAnswer.equals(answerChoice)) {
                        if (!poll.isMultipleChoice()) {
                            for (Object object : table_dates.getItems()) {
                                if (myAnswer.equals(object)) {
                                    table_dates.getSelectionModel().select(object);
                                }
                            }
                        } else
                            answerChoice.setCheckProperty(true);

                    }
                }
            }
            onLoad.put(User.getUser().getID(), myAnswersIDs);

            if (PollController.getInstance().getPollPromoterID(poll.getID()) == User.getUser().getID()) {
                updatePoll.setVisible(true);
                deletePoll.setVisible(true);
                if (!poll.isClosed())
                    closePoll.setVisible(true);
            } else {
                updatePoll.setVisible(false);
                deletePoll.setVisible(false);
                closePoll.setVisible(false);
            }

            if (poll.getType() != Poll.pollType.PRIVATE || (poll.getPromoterID() == User.getUser().getID()))
                sharePoll.setVisible(true);
            else
                sharePoll.setVisible(false);

            proposed_date.localDateTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (proposed_date.localDateTimeProperty().getValue() != null) {
                    proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
                    proposed_date.setLocalDateTime(null);
                }
            });

            chat.setFocusTraversable(false);
            updateChat.setGraphic(new ImageView(new Image("/images/update.png")));
            updateChat.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    getChat();
                }
            });

            message.setWrapText(true);
            //limit of 255 chars for a message
            message.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 255 ? change : null));

         getChat();
        }
    }

    /**
     * Populates the chat.
     */
    private void getChat() {
        chat.getItems().clear();
        messages = MessageController.getInstance().getPollMessages(poll.getID());
        List<VBox> vBoxes = new ArrayList<>();
        for (Message mess : messages) {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            Text info = new Text();
            Person sender = PersonController.getInstance().getPerson(mess.getSenderID());
            if(sender == null) {
                AlertManager.printError();
                ViewManager.switchView(ViewManager.viewsEnum.HOME);
                return;
            }
            else{
                String date = TimeManager.format("dd/MM/yyyy HH:mm:ss", mess.getCreationDate());
                info.setText(date + " " + sender.getFirstname() + " " + sender.getLastname() + " : ");
                hBox.getChildren().add(info);



                if(poll.getPromoterID()==User.getUser().getID() || mess.getSenderID()==User.getUser().getID()){
                    Button delete = new Button();
                    delete.setGraphic(new ImageView(new Image("/images/delete.png")));

                    delete.setOnAction(event -> {
                        int index = -1;
                        for (int i = 0; i < lines.size(); i++) {
                            HBox currentHBox = (HBox) lines.get(i).getChildren().get(0);
                            if (currentHBox.getChildren().size() > 1 && currentHBox.getChildren().get(1).equals(delete)) {
                                index = i;
                                i = lines.size();
                            }
                        }
                        if (index != -1) {
                            Optional<ButtonType> result = AlertManager.alertBox(Alert.AlertType.CONFIRMATION, "Suppression du message", null, "Voulez-vous vraiment supprimer ce message ?");
                            if (result.get() == ButtonType.OK) {
                                if(!MessageController.getInstance().delete(messages.get(index).getID())) {
                                    AlertManager.printError();
                                    ViewManager.switchView(ViewManager.viewsEnum.HOME);
                                    return;
                                }
                                else {
                                    messages.remove(messages.get(index));
                                }
                                getChat();
                            }
                        }
                    });
                    hBox.getChildren().add(delete);
                }
                StackPane container = new StackPane();
                Text content = new Text(mess.getContent());
                content.setWrappingWidth(250);
                container.getChildren().add(content);
                vBox.getChildren().addAll(hBox, container);
                vBox.setSpacing(5);
                vBoxes.add(vBox);
            }

        }
        lines.addAll(vBoxes);
    }

    /**
     * Populates the TableView.
     */
    public ObservableList<VBox> getLines() {
        return lines;
    }

    /**
     * Handles the actions done on the validate answer button.
     * Handles everything regarding the insertion or deletion of answer choices & answers.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleValidateAnswerButtonClick(ActionEvent actionEvent) {
        List<Integer> previousAnswersIDs = onLoad.get(User.getUser().getID());
        List<Integer> newAnswersIDs = new ArrayList<>();

        initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());

        //all the new answer choices added by the user
        List<AnswerChoice> newAnswerChoices = new ArrayList<>();
        for (AnswerChoice answerChoice : proposedDates) {
            if (!initialAnswerChoices.contains(answerChoice)) {
                newAnswerChoices.add(new AnswerChoice(null, answerChoice.getCreationDate(), answerChoice.getDateChoice(), poll.getID()));
            }
        }

        //all the new answers selected by the user
        List<Answer> answers = new ArrayList<>();
        if (!poll.isMultipleChoice()) {
            AnswerChoice answerChoice = (AnswerChoice) table_dates.getSelectionModel().getSelectedItem();
            if(!handleAnswers(newAnswerChoices, answerChoice, newAnswersIDs, answers)){
                AlertManager.printError();
                ViewManager.switchView(ViewManager.viewsEnum.HOME);
                return;
            }
        } else {
            for (Object obj : table_dates.getItems()) {
                AnswerChoice answerChoice = null;
                if (obj instanceof AnswerChoice) {
                    answerChoice = (AnswerChoice) obj;
                    if (answerChoice.isCheckProperty()) {
                        if(!handleAnswers(newAnswerChoices, answerChoice, newAnswersIDs, answers)){
                            AlertManager.printError();
                            ViewManager.switchView(ViewManager.viewsEnum.HOME);
                            return;
                        }
                    }
                }
            }
        }

        //insert of the new answer choices added but not selected by the user
        if(AnswerChoiceController.getInstance().addAll(newAnswerChoices) == null) {
            AlertManager.printError();
            ViewManager.switchView(ViewManager.viewsEnum.HOME);
            return;
        }

        //all the answers deselected by the user
        List<Integer> unselectedAnswers = new ArrayList<>();
        for (Integer id : previousAnswersIDs) {
            if (!newAnswersIDs.contains(id)) {
                unselectedAnswers.add(id);
            }
        }

        //deletion of the deselected answers
        if(!AnswerController.getInstance().deleteAll(poll.getID(), User.getUser().getID(), unselectedAnswers) ||
            AnswerController.getInstance().addAll(answers) == null ||
            !PollController.getInstance().updatePoll(poll)) {
            AlertManager.printError();
            return;
        }
        else{
            AlertManager.alertBox(Alert.AlertType.INFORMATION, "Information", null, "Merci de votre participation.");
            ViewManager.switchView(ViewManager.viewsEnum.HOME);
        }
    }

    /**
     * Handles the insertions of new answers and their answer choices if necessary.
     *
     * @param  newAnswerChoices  the list of answer choices that were added by the user
     * @param  answerChoice  the answer choice that we are currently processing
     * @param  newAnswersIDs  the list of new answer choices IDs
     * @param  answers the list of the user's answers
     * @return      true or false depending on the success of the insertions
     */
    private boolean handleAnswers(List<AnswerChoice> newAnswerChoices, AnswerChoice answerChoice, List<Integer> newAnswersIDs, List<Answer> answers){
        //if the answer choice was just created, we first need to add it into the database before inserting the answer
        if (newAnswerChoices.contains(answerChoice)) {
            answerChoice.setPollID(poll.getID());
            if (AnswerChoiceController.getInstance().addAndAnswer(User.getUser().getID(), answerChoice))
                newAnswerChoices.remove(answerChoice);
            else
                return false;
        }
        //if not we just insert the answer into the database
        else {
            newAnswersIDs.add(answerChoice.getID());
            answers.add(new Answer(User.getUser().getID(), poll.getID(), answerChoice.getID()));
        }
        return true;
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    /**
     * Handles the actions done on the update poll button.
     * Switches to the UpdatePoll window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleUpdatePollButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.UPDATE_POLL, poll);
    }

    /**
     * Handles the actions done on the share poll button.
     * Switches to the SharePoll window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleSharePollButtonClick(ActionEvent actionEvent) {
        ViewManager.openModal(ViewManager.viewsEnum.SHARE_POLL, poll);
    }

    public void handleReturnButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    /**
     * Sets all invitations to this poll as seen by the user.
     */
    private void pollInvitationSeen() {
        if (InvitationController.getInstance().wasInvitedToPoll(poll.getID(), User.getUser().getID()))
            if(!InvitationController.getInstance().setInvitationsAsSeen(poll.getID(), User.getUser().getID()))
                AlertManager.printError();
    }

    /**
     * Handles the actions done on the send message button for chatting.
     * Sends a message into the chat for the current poll.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleSendMessageButtonClick(ActionEvent actionEvent) {
        if (message.getText().length() > 0) {
            Message addedMessage = MessageController.getInstance().add(new Message(null, User.getUser().getID(), poll.getID(), message.getText(), null));
            if(addedMessage == null)
                AlertManager.printError();
            else{
                message.setText("");
                messages.add(addedMessage);
                getChat();
            }
        }
    }

    /**
     * Handles the actions done on the delete poll button.
     * Deletes the current poll if the promoter wants it.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleDeletePollButtonClick(ActionEvent actionEvent) {
        Optional<ButtonType> result = AlertManager.alertBox(Alert.AlertType.CONFIRMATION, "Suppression du sondage", null, "Êtes vous certain de vouloir supprimer ce sondage ?");
        if (result.get() == ButtonType.OK) {
            if (PollController.getInstance().deletePoll(poll.getID())) {
                ViewManager.switchView(ViewManager.viewsEnum.HOME);
                AlertManager.alertBox(Alert.AlertType.INFORMATION, "Sondage supprimé", null, "Suppression effectuée.");
            }
            else {
                AlertManager.printError();
            }
        }
    }

    /**
     * Handles the actions done on the close poll button.
     * Closes the current poll if the promoter wants it.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleClosePollButtonClick(ActionEvent actionEvent) {
        Optional<ButtonType> result = AlertManager.alertBox(Alert.AlertType.CONFIRMATION, "Cloturation du sondage", null, "La cloturation du sondage empêchera toutes personnes d'y accéder\n" +
                "Êtes vous certain de vouloir cloturer ce sondage ?");
        if (result.get() == ButtonType.OK) {
            if (PollController.getInstance().closePoll(true, poll.getID()))
                ViewManager.switchView(ViewManager.viewsEnum.RESULTS, poll);
            else
                AlertManager.printError();
        }
    }


}


