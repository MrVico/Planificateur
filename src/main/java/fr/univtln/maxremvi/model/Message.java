package fr.univtln.maxremvi.model;

import java.util.Date;

public class Message {
    private int id;
    private Person sender;
    private Poll poll;
    private String content;
    private Date creationDate;

    public Message(int id, Person sender, Poll poll, String content, Date creationDate) {
        this.id = id;
        this.sender = sender;
        this.poll = poll;
        this.content = content;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
