package fr.univtln.maxremvi.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Message {
    private Integer ID;
    private int senderID;
    private int pollID;
    private String content;
    private Date creationDate;

    public Message(Integer ID, int senderID, int pollID, String content, Date creationDate) {
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

    public static Message fromResultSet(ResultSet rs) throws SQLException {
        return new Message(rs.getInt("IDMESSAGE"), rs.getInt("IDPERSON"), rs.getInt("IDPOLL"), rs.getString("CONTENT"), rs.getTimestamp("CREATIONDATE"));
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
