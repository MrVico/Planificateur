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
        try {
            this.answerChoiceDao = DatabaseDao.getAnswerChoiceDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AnswerChoiceController getInstance(){
        if(answerChoiceController == null)
            return new AnswerChoiceController();
        else
            return answerChoiceController;
    }

    public List<AnswerChoice> getPollAnswerChoices(int idPoll){
        return answerChoiceDao.getPollAnswerChoices(idPoll);
    }

    public List<AnswerChoice> getPollAnswerChoicesForPerson(int idPoll, int idPerson) {
        return answerChoiceDao.getPollAnswerChoicesForPerson(idPoll, idPerson);
    }

    public AnswerChoice addAnswerChoice(Date creationDate, Date dateChoice, Integer idPoll)  throws SQLException, IOException {
        return answerChoiceDao.add(new AnswerChoice(null, creationDate, dateChoice, idPoll));
    }

    public List<AnswerChoice> addAll(List<AnswerChoice> answerChoices) throws SQLException {
        return answerChoiceDao.addAll(answerChoices);
    }

    public boolean addAndAnswer(int personID, AnswerChoice answerChoice) throws SQLException{
        return answerChoiceDao.addAndAnswer(personID, answerChoice);
    }
}
