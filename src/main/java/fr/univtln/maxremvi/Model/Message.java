package fr.univtln.maxremvi.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Message")
public class Message {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Person sender;
    @DatabaseField(canBeNull = false, foreign = true)
    private Poll poll;
    @DatabaseField(canBeNull = false)
    private String content;
    @DatabaseField(canBeNull = false)
    private Date creationDate;

    public Message(){}

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
