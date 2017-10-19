package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.AnswerDao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.model.AnswerChoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AnswerController {
    private AnswerDao answerDao = null;
    private static AnswerController answerController = null;

    private AnswerController() {
        try {
            this.answerDao = DatabaseDao.getAnswerDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static AnswerController getInstance(){
        if(answerController == null)
            return new AnswerController();
        else
            return answerController;
    }
    /*
    public Answer addAnswer(int idPerson, int idPoll, List<AnswerChoice> answerChoices)  throws SQLException, IOException {
        return answerDao.add(new Answer(idPerson, idPoll, answerChoices));
    }
    */
    public List<Answer> addAll(List<Answer> answers) throws SQLException {
        return answerDao.addAll(answers);
    }

    public boolean deleteAll(int idPoll, int idPerson, List<Integer> answerIDs) throws SQLException{
        return answerDao.deleteAll(idPoll, idPerson, answerIDs);
    }
}
