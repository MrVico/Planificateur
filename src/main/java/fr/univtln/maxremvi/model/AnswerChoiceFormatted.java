package fr.univtln.maxremvi.model;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.util.List;

public class AnswerChoiceFormatted {


    private SimpleStringProperty dateProperty;
    private ListProperty<SimpleStringProperty> hourProperties;
    private int nbAccessed = 0;

    public AnswerChoiceFormatted(SimpleStringProperty dateProperty, ListProperty<SimpleStringProperty> hourProperties) {
        this.dateProperty = dateProperty;
        this.hourProperties = hourProperties;
    }

    public String getDateProperty() {
        return dateProperty.get();
    }

    public SimpleStringProperty datePropertyProperty() {
        return dateProperty;
    }

    public void setDateProperty(String dateProperty) {
        this.dateProperty.set(dateProperty);
    }

    public ObservableList<SimpleStringProperty> getHourProperties() {
        return hourProperties.get();
    }

    public SimpleStringProperty hourPropertiesProperty() {
        System.out.println(dateProperty+" "+hourProperties+" "+hourProperties.size());
        if(nbAccessed < hourProperties.size()){
            SimpleStringProperty simpleStringProperty = hourProperties.get(nbAccessed);
            nbAccessed++;
            return simpleStringProperty;
        }
        else
            return null;
    }

    public void setHourProperties(ObservableList<SimpleStringProperty> hourProperties) {
        this.hourProperties.set(hourProperties);
    }

    @Override
    public String toString() {
        return "AnswerChoiceFormatted{" +
                "dateProperty=" + dateProperty +
                ", hourProperties=" + hourProperties +
                '}';
    }
}
