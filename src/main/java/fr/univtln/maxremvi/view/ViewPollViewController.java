package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.*;
import fr.univtln.maxremvi.model.*;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.ListManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import jfxtras.scene.control.LocalDateTimeTextField;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ViewPollViewController implements ViewControllerInterface {
    @FXML
    private Text title;
    @FXML
    private Text place;
    @FXML
    private Text description;
    @FXML
    private TableView table_dates;
    @FXML
    private Button updatePoll;
    @FXML
    private Button sharePoll;
    @FXML
    private Button deletePoll;
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
    //stocke l'état des réponses du sondage pour un utilisateur pour y accèder lors de la validation
    private Map<Integer, List<Integer>> onLoad = new HashMap<>();


    public void initialize(){
        if (poll != null) {
            pollInvitationSeen();
            title.setText(poll.getTitle());
            place.setText(poll.getLocation());
            description.setText(poll.getDescription());
            initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());
            proposedDates = ListManager.observableListFromList(initialAnswerChoices);
            table_dates.setEditable(true);
            TableColumn dateCol = new TableColumn("Date");
            dateCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("dateProperty"));
            TableColumn hourCol = new TableColumn("Heure");
            hourCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("hourProperty"));
            TableColumn<AnswerChoice, Boolean> checkCol = new TableColumn<>();
            checkCol.setCellValueFactory(new PropertyValueFactory<>("checkProperty"));
            checkCol.setCellFactory(column -> new CheckBoxTableCell());
            checkCol.setSortable(false);

            table_dates.getColumns().add(dateCol);
            table_dates.getColumns().add(hourCol);
            table_dates.getColumns().add(checkCol);

            if(!poll.isHideAnswers()){
                TableColumn answerCol = new TableColumn();
                answerCol.setCellValueFactory(new PropertyValueFactory<AnswerChoice, String>("timesChosenProperty"));
                table_dates.getColumns().add(answerCol);
            }

            table_dates.setItems(proposedDates);

            if(!poll.isAddDates())
                proposed_date.setDisable(true);

            List<AnswerChoice> myAnswers = AnswerChoiceController.getInstance().getPollAnswerChoicesForPerson(poll.getID(), User.getUser().getID());
            List<Integer> myAnswersIDs = new ArrayList<>();
            for(AnswerChoice myAnswer : myAnswers){
                myAnswersIDs.add(myAnswer.getID());
                for(AnswerChoice answerChoice : initialAnswerChoices){
                    if(myAnswer.equals(answerChoice)){
                        //TODO : Bizarre de faire des set dans la vue nan ?
                        answerChoice.setCheckProperty(true);
                    }
                }
            }
            onLoad.put(User.getUser().getID(), myAnswersIDs);

            if(PollController.getInstance().getPollPromoterID(poll.getID()) == User.getUser().getID()) {
                updatePoll.setVisible(true);
                deletePoll.setVisible(true);
            }
            else {
                updatePoll.setVisible(false);
                deletePoll.setVisible(false);
            }

            if(poll.getType()!=Poll.pollType.PRIVATE || (poll.getPromoterID()==User.getUser().getID()))
                sharePoll.setVisible(true);
            else
                sharePoll.setVisible(false);

            proposed_date.localDateTimeProperty().addListener((observable, oldValue, newValue) -> {
                if (proposed_date.localDateTimeProperty().getValue() != null) {
                    proposedDates.add(new AnswerChoice(null, Calendar.getInstance().getTime(), TimeManager.localDateToDate(proposed_date.getLocalDateTime()), null));
                    proposed_date.setLocalDateTime(null);
                }
            });

            //TODO : On peut toujours selectionner un item dans le chat, mais sinon le clic sur delete ne marche pas.
            //chat.setMouseTransparent(true);
            chat.setFocusTraversable(false);

            message.setWrapText(true);
            //limitation à 255 caractères pour un message
            message.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= 255 ? change : null));

            getChat();
        }
    }

    //populates the chat!
    public void getChat(){
        try {
            chat.getItems().clear();
            messages = MessageController.getInstance().getPollMessages(poll.getID());
            List<VBox> vBoxes = new ArrayList<>();
            for(Message mess : messages){
                VBox vBox = new VBox();
                HBox hBox = new HBox();
                Text info = new Text();
                Person sender = PersonController.getInstance().getPerson(mess.getSenderID());
                String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(mess.getCreationDate());
                info.setText(date+" "+sender.getFirstname()+" "+sender.getLastname()+" : ");
                hBox.getChildren().add(info);
                //TODO : Set icon & align to the right
                if(poll.getPromoterID()==User.getUser().getID() || mess.getSenderID()==User.getUser().getID()){
                    Button delete = new Button("X");
                    delete.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            int index = -1;
                            for(int i=0; i<lines.size(); i++){
                                HBox currentHBox = (HBox) lines.get(i).getChildren().get(0);
                                if(currentHBox.getChildren().size() > 1 && currentHBox.getChildren().get(1).equals(delete)) {
                                    index = i;
                                    i = lines.size();
                                }
                            }
                            if(index != -1){
                                Optional<ButtonType> result = AlertManager.AlertBox(Alert.AlertType.CONFIRMATION, "Suppression du message", null, "Voulez-vous vraiment supprimer ce message ?");
                                if (result.get() == ButtonType.OK){
                                    try {
                                        MessageController.getInstance().delete(messages.get(index).getID());
                                        messages.remove(messages.get(index));
                                        getChat();
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                }
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
            lines.addAll(vBoxes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<VBox> getLines() {
        return lines ;
    }

    @FXML
    public void handleValidateAnswerButtonClick(ActionEvent actionEvent) {
        List<Integer> previousAnswersIDs = onLoad.get(User.getUser().getID());
        List<Integer> newAnswersIDs = new ArrayList<>();

        //TODO : Normalement ça ne devrait pas être nécessaire ?!?
        initialAnswerChoices = AnswerChoiceController.getInstance().getPollAnswerChoices(poll.getID());

        //récupération des nouveaux choix de réponses ajoutés par l'utilisateur
        List<AnswerChoice> newAnswerChoices = new ArrayList<>();
        for(AnswerChoice answerChoice : proposedDates){
            if(!initialAnswerChoices.contains(answerChoice)){
                newAnswerChoices.add(new AnswerChoice(null, answerChoice.getCreationDate(), answerChoice.getDateChoice(), poll.getID()));
            }
        }

        //récupèration des réponses sélectionnées par l'utilisateur
        List<Answer> answers = new ArrayList<>();
        for(Object obj : table_dates.getItems()){
            AnswerChoice answerChoice = null;
            if(obj instanceof AnswerChoice) {
                answerChoice = (AnswerChoice) obj;
                if(answerChoice.isCheckProperty()) {
                    //si l'utilisateur a ajouté le choix de réponse il faut d'abord le créé
                    if(newAnswerChoices.contains(answerChoice)) {
                        try {
                            //TODO : Bizarre de faire des set dans la vue nan ?
                            answerChoice.setPollID(poll.getID());
                            if(AnswerChoiceController.getInstance().addAndAnswer(User.getUser().getID(), answerChoice))
                                newAnswerChoices.remove(answerChoice);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    //sinon on ajoute juste la réponse
                    else{
                        newAnswersIDs.add(answerChoice.getID());
                        answers.add(new Answer(User.getUser().getID(), poll.getID(), answerChoice.getID()));
                    }
                }
            }
        }

        //ajout des nouveaux choix de réponses non sélectionnés
        List<AnswerChoice> insertedNewAnswerChoices = new ArrayList<>();
        try {
            insertedNewAnswerChoices = AnswerChoiceController.getInstance().addAll(newAnswerChoices);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //récupèration des réponses désélectionnées par l'utilisateur
        List<Integer> unselectedAnswers = new ArrayList<>();
        for(Integer id : previousAnswersIDs){
            if(!newAnswersIDs.contains(id)){
                unselectedAnswers.add(id);
            }
        }

        //suppression de toutes les réponses désélectionnées par l'utilisateur
        try {
            AnswerController.getInstance().deleteAll(poll.getID(), User.getUser().getID(), unselectedAnswers);
            AnswerController.getInstance().addAll(answers);
            AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "Merci de votre participation.");
            ViewManager.switchView(ViewManager.viewsEnum.HOME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData(Object... arguments) {
        this.poll = (Poll) arguments[0];
        initialize();
    }

    public void handleUpdatePollButtonClick(ActionEvent actionEvent) {
        AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Information", null, "TODO !!!");
    }

    public void handleSharePollButtonClick(ActionEvent actionEvent) {
        ViewManager.openModal(ViewManager.viewsEnum.SHARE_POLL, poll);
    }

    public void handleReturnButtonClick(ActionEvent actionEvent)
    {
        ViewManager.switchView(ViewManager.viewsEnum.HOME);
    }

    public void pollInvitationSeen()
    {
        try {
            if(InvitationController.getInstance().wasInvitedToPoll(poll.getID(), User.getUser().getID()))
                InvitationController.getInstance().setInvitationsAsSeen(poll.getID(), User.getUser().getID());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleSendMessageButtonClick(ActionEvent actionEvent) {
        if(message.getText().length()>0){
            try {
                Message addedMessage = MessageController.getInstance().add(new Message(null, User.getUser().getID(), poll.getID(), message.getText(), null));
                message.setText("");
                messages.add(addedMessage);
                getChat();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleDeletePollButtonClick(ActionEvent actionEvent) {
        Optional<ButtonType> result = AlertManager.AlertBox(Alert.AlertType.CONFIRMATION, "Suppression du sondage", null, "Êtes vous certain de vouloir supprimer ce sondage ?");
        if (result.get() == ButtonType.OK){
            try {
                if(PollController.getInstance().deletePoll(poll.getID())) {
                    ViewManager.switchView(ViewManager.viewsEnum.HOME);
                    AlertManager.AlertBox(Alert.AlertType.INFORMATION, "Sondage supprimé", null, "Suppression effectuée.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
