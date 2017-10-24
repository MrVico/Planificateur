package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.utils.TimeManager;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AnswerChoice {
    private Integer ID;
    private Integer pollID;
    private Date creationDate;
    private Date dateChoice;
    private SimpleStringProperty dateProperty;
    private SimpleStringProperty hourProperty;
    private SimpleBooleanProperty checkProperty;
    private SimpleStringProperty timesChosenProperty;

    public AnswerChoice(Integer ID, Date creationDate, Date dateChoice, Integer pollID) {
        this.ID = ID;
        this.creationDate = creationDate;
        this.dateChoice = dateChoice;
        this.dateProperty = new SimpleStringProperty(TimeManager.extractDateString(dateChoice));
        this.hourProperty = new SimpleStringProperty(TimeManager.extractHourMinutesString(dateChoice));
        this.checkProperty = new SimpleBooleanProperty(false);
        this.pollID = pollID;
    }

    public AnswerChoice(int ID, Date creationDate, Date dateChoice, int pollID, String timesChosen) {
        this.ID = ID;
        this.creationDate = creationDate;
        this.dateChoice = dateChoice;
        this.dateProperty = new SimpleStringProperty(TimeManager.extractDateString(dateChoice));
        this.hourProperty = new SimpleStringProperty(TimeManager.extractHourMinutesString(dateChoice));
        this.checkProperty = new SimpleBooleanProperty(false);
        this.timesChosenProperty = new SimpleStringProperty("("+timesChosen+")");
        this.pollID = pollID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public boolean isCheckProperty() {
        return checkProperty.get();
    }

    public SimpleBooleanProperty checkPropertyProperty() {
        return checkProperty;
    }

    public void setCheckProperty(boolean checkProperty) {
        this.checkProperty.set(checkProperty);
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public String getTimesChosenProperty() {
        return timesChosenProperty.get();
    }

    public SimpleStringProperty timesChosenPropertyProperty() {
        return timesChosenProperty;
    }

    public void setTimesChosenProperty(String timesChosenProperty) {
        this.timesChosenProperty.set(timesChosenProperty);
    }

    public static AnswerChoice fromResultSet(ResultSet rs) throws SQLException {
        return new AnswerChoice(
                rs.getInt("IDANSWERCHOICE"),
                rs.getDate("CREATIONDATE"),
                rs.getDate("DATECHOICE"),
                rs.getInt("IDPOLL")
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerChoice that = (AnswerChoice) o;

        if (!dateProperty.toString().equals(that.dateProperty.toString())) return false;
        return hourProperty.toString().equals(that.hourProperty.toString());
    }

    @Override
    public int hashCode() {
        int result = dateProperty.hashCode();
        result = 31 * result + hourProperty.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm").format(dateChoice)+" "+timesChosenProperty.get();
    }
}
