package fr.univtln.maxremvi.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Invitation {
    private int personID;
    private int pollID;
    private int senderID;
    private boolean seen;

    public Invitation(){}

    public Invitation(int pollID, int personID,  int senderID, boolean seen) {
        this.pollID = pollID;
        this.personID = personID;
        this.senderID = senderID;
        this.seen = seen;
    }

    public static Invitation fromResusltSet(ResultSet rs) throws SQLException
    {
        System.out.println("coucou");
        return new Invitation(rs.getInt("IDPOLL"), rs.getInt("IDPERSON"), rs.getInt("IDPERSONINVITER"), rs.getBoolean("SEEN"));

    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getPollID() {
        return pollID;
    }

    public void setPollID(int pollID) {
        this.pollID = pollID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
    
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "personID=" + personID +
                ", pollID=" + pollID +
                ", senderID=" + senderID +
                ", seen=" + seen +
                '}';
    }
}
