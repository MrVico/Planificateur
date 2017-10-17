package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.utils.TimeManager;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class PollDate {
    private Date date;
    private SimpleStringProperty dateProperty;
    private SimpleStringProperty hourProperty;

    public PollDate(Date date) {
        this.date = date;
        this.dateProperty = new SimpleStringProperty(TimeManager.extractDateString(date));
        this.hourProperty = new SimpleStringProperty(TimeManager.extractHourMinutesString(date));
    }

    public String getDateProperty() {
        return dateProperty.get();
    }

    public void setDateProperty(String v) {
        dateProperty.set(v);
    }

    public String getHourProperty() {
        return hourProperty.get();
    }

    public void setHourProperty(String v) {
        hourProperty.set(v);
    }
}
