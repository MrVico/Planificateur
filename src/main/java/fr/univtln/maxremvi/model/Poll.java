package fr.univtln.maxremvi.model;

import java.util.Date;

public class Poll {
    private int id;
    private String title;
    private String description;
    private String location;
    private Date closingDate;
    private boolean closed;
    private Person idPerson;

    public static enum type {
      PUBLIC, PRIVATE_SHARABLE, PRIVATE
    };

    public Poll(){}

    public Poll(int id, String title, String description, String location, Date closingDate, boolean closed, Person promoter) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.closingDate = closingDate;
        this.closed = closed;
        this.idPerson = promoter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Person getPromoter() {
        return idPerson;
    }

    public void setPromoter(Person promoter) {
        this.idPerson = promoter;
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
