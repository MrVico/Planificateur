package fr.univtln.maxremvi.Model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Answer")
public class Answer {
    @DatabaseField(foreign = true)
    private Person person;
    //@DatabaseField(foreign = true)
    private Poll poll;
    @DatabaseField(canBeNull = false)
    private Date creationDate;
    @ForeignCollectionField(eager = false)
    ForeignCollection<AnswerChoice> answerChoices;

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
}
