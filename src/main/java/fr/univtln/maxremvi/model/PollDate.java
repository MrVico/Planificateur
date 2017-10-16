package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.utils.TimeUtil;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class PollDate {
    private Date date;
    private SimpleStringProperty dateProperty;
    private SimpleStringProperty hourProperty;

    public PollDate(Date date) {
        this.date = date;
        this.dateProperty = new SimpleStringProperty(TimeUtil.extractDateString(date));
        this.hourProperty = new SimpleStringProperty(TimeUtil.extractHourMinutesString(date));
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
