package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.utils.TimeUtil;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class AnswerChoice {
    private int id;
    private Date creationDate;
    private Date dateChoice;
    private SimpleStringProperty dateProperty;
    private SimpleStringProperty hourProperty;

    public AnswerChoice(int id, Date creationDate, Date dateChoice) {
        this.id = id;
        this.creationDate = creationDate;
        this.dateChoice = dateChoice;
        this.dateProperty = new SimpleStringProperty(TimeUtil.extractDateString(dateChoice));
        this.hourProperty = new SimpleStringProperty(TimeUtil.extractHourMinutesString(dateChoice));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDateChoice() {
        return dateChoice;
    }

    public void setDateChoice(Date dateChoice) {
        this.dateChoice = dateChoice;
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
