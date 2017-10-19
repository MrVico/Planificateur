package fr.univtln.maxremvi.model;

import java.util.Date;
import java.util.List;

public class Answer {
    //private Person person;
    //private Poll poll;
    private int idPerson;
    private int idPoll;
    private int idAnswerChoice;

    public Answer(){}

    public Answer(int idPerson, int idPoll, int idAnswerChoice) {
        this.idPerson = idPerson;
        this.idPoll = idPoll;
        this.idAnswerChoice = idAnswerChoice;
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

    public int getIdAnswerChoice() {
        return idAnswerChoice;
    }

    public void setIdAnswerChoice(int idAnswerChoice) {
        this.idAnswerChoice = idAnswerChoice;
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
        */

    @Override
    public String toString() {
        return "Answer{" +
                "idPerson=" + idPerson +
                ", idPoll=" + idPoll +
                ", idAnswerChoice=" + idAnswerChoice +
                '}';
    }
}
