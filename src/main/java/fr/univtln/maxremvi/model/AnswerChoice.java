package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.utils.TimeManager;
import javafx.beans.property.SimpleStringProperty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AnswerChoice {
    private Integer id;
    private Integer idPoll;
    private Date creationDate;
    private Date dateChoice;
    private SimpleStringProperty dateProperty;
    private SimpleStringProperty hourProperty;

    public AnswerChoice(Integer id, Date creationDate, Date dateChoice, Integer idPoll) {
        this.id = id;
        this.creationDate = creationDate;
        this.dateChoice = dateChoice;
        this.dateProperty = new SimpleStringProperty(TimeManager.extractDateString(dateChoice));
        this.hourProperty = new SimpleStringProperty(TimeManager.extractHourMinutesString(dateChoice));
        this.idPoll = idPoll;
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

    public int getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(int idPoll) {
        this.idPoll = idPoll;
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
    public String toString() {
        return "AnswerChoice{" +
                "id=" + id +
                ", idPoll=" + idPoll +
                ", creationDate=" + creationDate +
                ", dateChoice=" + dateChoice +
                ", dateProperty=" + dateProperty +
                ", hourProperty=" + hourProperty +
                '}';
    }
}
