package fr.univtln.maxremvi.model;

import java.util.Date;

public class Message {
    private int ID;
    private int senderID;
    private int pollID;
    private String content;
    private Date creationDate;

    public Message(int ID, int senderID, int pollID, String content, Date creationDate) {
        this.ID = ID;
        this.senderID = senderID;
        this.pollID = pollID;
        this.content = content;
        this.creationDate = creationDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
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

    @Override
    public String toString() {
        return "Message{" +
                "ID=" + ID +
                ", sender=" + senderID +
                ", poll=" + pollID +
                ", content='" + content + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
