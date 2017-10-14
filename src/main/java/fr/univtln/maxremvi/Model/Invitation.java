package fr.univtln.maxremvi.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Invitation")
public class Invitation {

    @DatabaseField(foreign = true)
    private Person person;
    @DatabaseField(foreign = true)
    private Poll poll;
    @DatabaseField(foreign = true)
    private Person inviter;
    @DatabaseField(canBeNull = false)
    private boolean seen;
    @DatabaseField(canBeNull = false)
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
