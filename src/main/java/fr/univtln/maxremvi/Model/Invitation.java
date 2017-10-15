package fr.univtln.maxremvi.Model;

import java.util.Date;

public class Invitation {

    private Person person;
    private Poll poll;
    private Person inviter;
    private boolean seen;
    private Date sendingDate;

    public Invitation(){}

    public Invitation(Person person, Poll poll, Person inviter, boolean seen, Date sendingDate) {
        this.person = person;
        this.poll = poll;
        this.inviter = inviter;
        this.seen = seen;
        this.sendingDate = sendingDate;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Person getInviter() {
        return inviter;
    }

    public void setInviter(Person inviter) {
        this.inviter = inviter;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }
}
