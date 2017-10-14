package fr.univtln.maxremvi.View;

import fr.univtln.maxremvi.Controller.PersonController;
import fr.univtln.maxremvi.Controller.PollController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ListView;

public class HomeViewController {
    private final ObservableList<HBox> lines = FXCollections.observableArrayList();
    private PollController pollController;

    public void initialize(){
        pollController = new PollController();
        try {
            pollController.getPolls();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HBox hBox1 = new HBox();
        Label label11 = new Label("Titre du sondage 1");
        label11.setPrefWidth(150);
        Label label12 = new Label("Nom de l'organisateur");
        label12.setPrefWidth(150);
        Label label13 = new Label("Date de l'évènement 1");
        label13.setPrefWidth(150);
        hBox1.getChildren().addAll(label11, label12, label13);
        HBox hBox2 = new HBox();
        hBox2.getChildren().add(new Label("Titre du sondage 2"));
        hBox2.getChildren().add(new Label("Nom de l'organisateur"));
        hBox2.getChildren().add(new Label("Date de l'évènement 2"));
        HBox hBox3 = new HBox();
        hBox3.getChildren().add(new Label("Titre du sondage 3"));
        hBox3.getChildren().add(new Label("Nom de l'organisateur"));
        hBox3.getChildren().add(new Label("Date de l'évènement 3"));
        lines.addAll(hBox1, hBox2, hBox3);
    }

    public ObservableList<HBox> getLines() {
        return lines ;
    }

    @FXML
    public void handleClick(ActionEvent actionEvent){
        System.out.println("CLICK");
        //panel.getChildren().add(new TextField("Please fill in..."));
    }
}
