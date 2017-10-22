package fr.univtln.maxremvi.model;

public class Answer {
    private int personID;
    private int pollID;
    private int answerChoiceID;

    public Answer(){}

    public Answer(int personID, int pollID, int answerChoiceID) {
        this.personID = personID;
        this.pollID = pollID;
        this.answerChoiceID = answerChoiceID;
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

    public int getAnswerChoiceID() {
        return answerChoiceID;
    }

    public void setAnswerChoiceID(int answerChoiceID) {
        this.answerChoiceID = answerChoiceID;
    }
    
    @Override
    public String toString() {
        return "Answer{" +
                "personID=" + personID +
                ", pollID=" + pollID +
                ", answerChoiceID=" + answerChoiceID +
                '}';
    }
}
