package fr.univtln.maxremvi.Model;

import java.util.Date;

public class AnswerChoice {
    private int id;
    private Poll poll;
    private String description;
    private Date creationDate;
    private Answer answer;

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
