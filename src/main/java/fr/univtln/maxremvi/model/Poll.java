package fr.univtln.maxremvi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Poll {
    private Integer id;
    private int idPerson;
    private String title;
    private String description;
    private String location;
    private Date closingDate;
    private boolean closed;
    private boolean multipleChoice;
    private boolean hideAnswers;
    private boolean addDates;
    private pollType type;

    public enum pollType {
      PUBLIC, PRIVATE_SHARABLE, PRIVATE;
    };
    public Poll(){}

    public Poll(Integer id, String title, String description, String location, Date closingDate, boolean closed, int idPerson, boolean multipleChoice, boolean hideAnswers, boolean addDates, pollType pollType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.closingDate = closingDate;
        this.closed = closed;
        this.idPerson = idPerson;
        this.multipleChoice = multipleChoice;
        this.hideAnswers = hideAnswers;
        this.addDates = addDates;
        this.type = pollType;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getPromoterID() {
        return idPerson;
    }

    public void setPromoter(int idPerson) {
        this.idPerson = idPerson;
    }

    public boolean isMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public boolean isHideAnswers() {
        return hideAnswers;
    }

    public void setHideAnswers(boolean hideAnswers) {
        this.hideAnswers = hideAnswers;
    }

    public boolean isAddDates() {
        return addDates;
    }

    public void setAddDates(boolean addDates) {
        this.addDates = addDates;
    }

    public pollType getType() {
        return type;
    }

    public void setType(pollType type) {
        this.type = type;
    }

    public static Poll fromResultSet(ResultSet rs) throws SQLException {
        return new Poll(rs.getInt("ID"), rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("LOCATION"), rs.getDate("CLOSINGDATE"),
                rs.getBoolean("CLOSED"), rs.getInt("IDPERSON"), rs.getBoolean("MULTIPLECHOICE"), rs.getBoolean("HIDEANSWERS"), rs.getBoolean("ADDDATES"), Poll.pollType.valueOf(rs.getString("TYPE")));
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", closingDate=" + closingDate +
                ", closed=" + closed +
                ", idPerson=" + idPerson +
                '}';
    }
}
