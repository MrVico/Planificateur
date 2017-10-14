package fr.univtln.maxremvi.View;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javax.swing.text.html.ListView;

public class HomeController {
    @FXML
    private ListView pollList;
    @FXML
    private Pane panel;

    public void initialize(){
        //panel.getChildren().add(new TextField("Please fill in..."));
    }

    @FXML
    public void handleClick(ActionEvent actionEvent){
        System.out.println("CLICK");
        //panel.getChildren().add(new TextField("Please fill in..."));
    }
}
