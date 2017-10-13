package fr.univtln.maxremvi.Model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Poll")
public class Invitation {

    @DatabaseField(foreign = true)
    private Person idPerson;
    @DatabaseField(foreign = true)
    private Poll idPoll;
    @DatabaseField(canBeNull = false)
    private Person inviter;
    @DatabaseField(canBeNull = false)
    private boolean seen;
    @DatabaseField(canBeNull = false)
    private Date sendingDate;

    public Invitation(){}

    public Invitation(Person idPerson, Poll idPoll, Person inviter, boolean seen, Date sendingDate) {
        this.idPerson = idPerson;
        this.idPoll = idPoll;
        this.inviter = inviter;
        this.seen = seen;
        this.sendingDate = sendingDate;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public Poll getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(Poll idPoll) {
        this.idPoll = idPoll;
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
