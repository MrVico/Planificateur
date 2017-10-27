package fr.univtln.maxremvi.view;

import fr.univtln.maxremvi.controller.InvitationController;
import fr.univtln.maxremvi.controller.PersonController;
import fr.univtln.maxremvi.controller.PollController;
import fr.univtln.maxremvi.model.Invitation;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.AlertManager;
import fr.univtln.maxremvi.utils.TimeManager;
import fr.univtln.maxremvi.utils.ViewManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeViewController implements ViewControllerInterface {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private List<Poll> pollList;
    private List<Invitation> invitationList;
    @FXML
    private ListView listView;
    @FXML
    private Button refreshButton;

    /**
     * Initializes the HomeView window.
     */
    public void initialize(){
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getClickCount()==2 && listView.getSelectionModel().getSelectedItem()!=null){
                    Poll selectedPoll = pollList.get(listView.getSelectionModel().getSelectedIndex());
                    if(!selectedPoll.isClosed())
                        ViewManager.switchView(ViewManager.viewsEnum.VIEW_POLL, selectedPoll);
                    else{
                        if(selectedPoll.getPromoterID()==User.getUser().getID())
                            ViewManager.switchView(ViewManager.viewsEnum.RESULTS, selectedPoll);
                        else
                            AlertManager.alertBox(Alert.AlertType.INFORMATION, "Résultat", null, "La réunion se déroulera le "+
                                    TimeManager.format("dd/MM/yyyy HH:mm", selectedPoll.getFinalDate())+".");
                    }
                }
            }
        });

        refreshButton.setGraphic(new ImageView(new Image("/images/update.png")));
        getPolls();
    }

    /**
     * Populates the ListView with all the polls the current user has access to.
     */
    private void getPolls(){
        try {
            listView.getItems().clear();
            invitationList = InvitationController.getInstance().getAll();
            pollList = PollController.getInstance().getVisiblePollsForPerson(User.getUser());
            List<HBox> hBoxes = new ArrayList<>();
            Label labelC;
            if(pollList != null){
                for(Poll poll : pollList){
                    HBox hBox = new HBox();
                    hBox.setPrefHeight(20);
                    Label labelA = new Label(poll.getTitle());
                    labelA.setPrefWidth(280);
                    Person promoter = PersonController.getInstance().getPerson(poll.getPromoterID());
                    Label labelB = new Label(promoter.getFirstname()+" "+promoter.getLastname());
                    labelB.setPrefWidth(160);
                    labelC = new Label(TimeManager.extractDateString(poll.getCreationDate()));
                    labelC.setPrefWidth(85);
                    hBox.getChildren().addAll(labelA, labelB, labelC);
                    if(!poll.isClosed()) {
                        Invitation invitation = null;
                        for (Invitation invite : invitationList) {
                            if (invite.getPollID() == poll.getID())
                                invitation = invite;
                        }
                        if (invitation != null) {
                            Button invitationButton = new Button();
                            Image image = new Image("/images/invitation.png");
                            invitationButton.setGraphic(new ImageView(image));
                            invitationButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Optional<ButtonType> result = AlertManager.alertBox(Alert.AlertType.CONFIRMATION, "Invitation", null, "Marquer comme lue ?");
                                    if (result.get() == ButtonType.OK) {
                                        if(InvitationController.getInstance().setInvitationsAsSeen(poll.getID(), User.getUser().getID()))
                                            getPolls();
                                        else
                                            AlertManager.printError();
                                    }
                                }
                            });
                            hBox.getChildren().add(invitationButton);
                        }
                    }
                    else{
                        Button viewResultsButton = new Button();
                        Image image = new Image("/images/results.png");
                        viewResultsButton.setGraphic(new ImageView(image));
                        viewResultsButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                if(poll.getPromoterID()==User.getUser().getID())
                                    ViewManager.switchView(ViewManager.viewsEnum.RESULTS, poll);
                                else
                                    AlertManager.alertBox(Alert.AlertType.INFORMATION, "Résultat", null, "La réunion se déroulera le "+
                                            TimeManager.format("dd/MM/yyyy HH:mm", poll.getFinalDate())+".");
                            }
                        });
                        hBox.getChildren().add(viewResultsButton);

                        if(poll.getPromoterID()==User.getUser().getID()){
                            Button openButton = new Button();
                            image = new Image("/images/closed.png");
                            openButton.setGraphic(new ImageView(image));
                            openButton.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    Optional<ButtonType> result = AlertManager.alertBox(Alert.AlertType.CONFIRMATION, "Reouverture", null, "Voulez-vous vraiment réouvrir ce sondage ?");
                                    if (result.get() == ButtonType.OK) {
                                        if(PollController.getInstance().closePoll(false, poll.getID()))
                                            getPolls();
                                        else
                                            AlertManager.printError();
                                    }
                                }
                            });
                            hBox.getChildren().add(openButton);
                        }
                    }
                    hBoxes.add(hBox);
                }
                lines.addAll(hBoxes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populates the ListView.
     */
    public ObservableList<HBox> getLines() {
        return lines ;
    }

    /**
     * Handles the actions done on the creation poll button.
     * Switches to the CreatePoll window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleCreatePollButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.CREATE_POLL);
    }

    /**
     * Handles the actions done on the view profil button.
     * Switches to the ViewProfil window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleViewProfilButtonClick(ActionEvent actionEvent) {
        ViewManager.switchView(ViewManager.viewsEnum.VIEW_PROFIL);
    }

    /**
     * Handles the actions done on the disconnection button.
     * Disconnects the user and switches to the Home window.
     *
     * @param  actionEvent  the type of action that was performed
     */
    public void handleDisconnectionButtonClick(ActionEvent actionEvent){
        User.setUser(null);
        ViewManager.switchView(ViewManager.viewsEnum.SIGNIN);
    }

    /**
     * Handles the actions done on the refresh button.
     * Refreshes the list of polls.
     *
     * @param  event  the type of action that was performed
     */
    public void handleRefreshButtonClick(ActionEvent event) {
        getPolls();
    }

    @Override
    public void initData(Object... arguments) {

    }
}
