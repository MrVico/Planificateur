package fr.univtln.maxremvi.model;

import java.util.Date;
import java.util.List;

public class Answer {
    private Person person;
    private Poll poll;
    private Date creationDate;
    private List<AnswerChoice> answerChoices;

    public Answer(){}

    public Answer(Person person, Poll poll, Date creationDate) {
        this.person = person;
        this.poll = poll;
        this.creationDate = creationDate;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "person=" + person +
                ", poll=" + poll +
                ", creationDate=" + creationDate +
                ", answerChoices=" + answerChoices +
                '}';
    }
}
