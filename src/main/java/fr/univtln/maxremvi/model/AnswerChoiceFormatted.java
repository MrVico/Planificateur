package fr.univtln.maxremvi.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class AnswerChoiceFormatted {


    private SimpleStringProperty dateProperty;
    private ListProperty<SimpleStringProperty> hourProperties;

    public AnswerChoiceFormatted(SimpleStringProperty dateProperty, ListProperty<SimpleStringProperty> hourProperties) {
        this.dateProperty = dateProperty;
        this.hourProperties = hourProperties;
    }

    @Override
    public String toString() {
        return "AnswerChoiceFormatted{" +
                "dateProperty=" + dateProperty +
                ", hourProperties=" + hourProperties +
                '}';
    }
}
