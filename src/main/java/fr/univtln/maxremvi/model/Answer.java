package fr.univtln.maxremvi.model;

import java.util.Date;
import java.util.List;

public class Answer {
    //private Person person;
    //private Poll poll;
    private int idPerson;
    private int idPoll;
    private List<AnswerChoice> answerChoices;

    public Answer(){}

    public Answer(int idPerson, int idPoll, List<AnswerChoice> answerChoices) {
        this.idPerson = idPerson;
        this.idPoll = idPoll;
        this.answerChoices = answerChoices;
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

    public List<AnswerChoice> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<AnswerChoice> answerChoices) {
        this.answerChoices = answerChoices;
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
                "person=" + idPerson +
                ", poll=" + idPoll +
                ", answerChoices=" + answerChoices +
                '}';
    }
}
