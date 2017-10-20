package fr.univtln.maxremvi.model;

import java.util.Date;

public class Invitation {

    /*
    private Person person;
    private Poll poll;
    private Person inviter;
    */
    private int idPerson;
    private int idPoll;
    private int idSender;
    private boolean seen;

    public Invitation(){}
    /*
    public Invitation(Person person, Poll poll, Person inviter, boolean seen, Date sendingDate) {
        this.person = person;
        this.poll = poll;
        this.inviter = inviter;
        this.seen = seen;
        this.sendingDate = sendingDate;
    }
    */

    public Invitation(int idPerson, int idPoll, int idSender, boolean seen) {
        this.idPerson = idPerson;
        this.idPoll = idPoll;
        this.idSender = idSender;
        this.seen = seen;
    }

    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    public int getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(int idPoll) {
        this.idPoll = idPoll;
    }

    public int getIdSender() {
        return idSender;
    }

    public void setIdSender(int idSender) {
        this.idSender = idSender;
    }

    /*
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
        */
    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "idPerson=" + idPerson +
                ", idPoll=" + idPoll +
                ", idSender=" + idSender +
                ", seen=" + seen +
                '}';
    }
}
