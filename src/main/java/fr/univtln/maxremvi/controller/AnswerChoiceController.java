package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.database.AnswerChoiceDao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.model.AnswerChoice;

import java.sql.SQLException;
import java.util.List;

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
        return answerChoiceDao.getAnswerChoices(idPoll);
    }
}
