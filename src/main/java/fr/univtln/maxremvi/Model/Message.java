package fr.univtln.maxremvi.Model;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class Message {
    @DatabaseField(id = true)
    private int idMessage;
    @DatabaseField(canBeNull = false)
    private Person sender;
    @DatabaseField(canBeNull = false)
    private Poll poll;
    @DatabaseField(canBeNull = false)
    private String content;
    @DatabaseField(canBeNull = false)
    private Date creationDate;

    public Message(){}

    public Message(int idMessage, Person sender, Poll poll, String content, Date creationDate) {
        this.idMessage = idMessage;
        this.sender = sender;
        this.poll = poll;
        this.content = content;
        this.creationDate = creationDate;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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
