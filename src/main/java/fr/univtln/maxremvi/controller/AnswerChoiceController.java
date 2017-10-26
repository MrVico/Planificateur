package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.AnswerChoiceDao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.model.AnswerChoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by remi on 17/10/2017.
 */
public class AnswerChoiceController {
    private AnswerChoiceDao answerChoiceDao = null;
    private static AnswerChoiceController answerChoiceController = null;

    private AnswerChoiceController() {
        this.answerChoiceDao = DatabaseDao.getAnswerChoiceDao();
    }

    public static AnswerChoiceController getInstance() {
        if (answerChoiceController == null)
            return new AnswerChoiceController();
        else
            return answerChoiceController;
    }

    public List<AnswerChoice> getPollAnswerChoices(int idPoll) {
        return answerChoiceDao.getPollAnswerChoices(idPoll);
    }

    public List<AnswerChoice> getPollAnswerChoicesForPerson(int idPoll, int idPerson) {
        return answerChoiceDao.getPollAnswerChoicesForPerson(idPoll, idPerson);
    }

    public AnswerChoice addAnswerChoice(Date creationDate, Date dateChoice, Integer idPoll) {
        return answerChoiceDao.add(new AnswerChoice(null, creationDate, dateChoice, idPoll));
    }

    public List<AnswerChoice> addAll(List<AnswerChoice> answerChoices) {
        return answerChoiceDao.addAll(answerChoices);
    }

    public boolean addAndAnswer(int personID, AnswerChoice answerChoice) {
        return answerChoiceDao.addAndAnswer(personID, answerChoice);
    }

    public boolean delete(int answerChoiceID) {
        return answerChoiceDao.remove(answerChoiceID);
    }
}
