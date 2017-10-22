package fr.univtln.maxremvi.model;

public class Invitation {
    private int personID;
    private int pollID;
    private int senderID;
    private boolean seen;

    public Invitation(){}

    public Invitation(int personID, int pollID, int senderID, boolean seen) {
        this.personID = personID;
        this.pollID = pollID;
        this.senderID = senderID;
        this.seen = seen;
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
