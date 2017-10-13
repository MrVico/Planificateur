package fr.univtln.maxremvi.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "AnswerChoice")
public class AnswerChoice {
    @DatabaseField(columnName = "idAnswerChoice", id = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private Poll poll;
    @DatabaseField(canBeNull = false)
    private String description;
    @DatabaseField(canBeNull = false)
    private Date creationDate;

    public AnswerChoice(){}

    public AnswerChoice(int id, Poll poll, String description, Date creationDate) {
        this.id = id;
        this.poll = poll;
        this.description = description;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
