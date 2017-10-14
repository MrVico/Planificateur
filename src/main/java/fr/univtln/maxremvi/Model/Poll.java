package fr.univtln.maxremvi.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Poll")
public class Poll {
    //utile ?
    private static int ID = 0;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private String location;
    @DatabaseField
    private Date closingDate;
    @DatabaseField
    private boolean closed;
    @DatabaseField(foreign = true)
    private Person idPerson;

    public Poll(){}

    public Poll(String title, String description, String location, Date closingDate, boolean closed, Person promoter) {
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
}
