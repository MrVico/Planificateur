package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class AnswerDao extends AbstractDao<Answer>{
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new AnswerDao();
        return instance;
    }

    @Override
    public Answer get(int id) {
        return null;
    }

    public boolean answerExists(int idPoll, int idPerson){
        try {
            String query = "SELECT * FROM ANSWER WHERE IDPOLL = ? AND IDPERSON = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll, idPerson);
            if(!rs.next())
                return false;
            else
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Answer> getAll() {
        return null;
    }

    @Override
    public Answer add(Answer object) throws SQLException {
        //TODO : Ajouter/Supprimer les réponses nouvelles
        if(!answerExists(object.getIdPoll(), object.getIdPerson())){
            String query = "INSERT INTO ANSWER(IDPOLL, IDPERSON, CREATIONDATE) VALUES(?, ?, ?)";
            DatabaseUtil.executeUpdate(query, object.getIdPoll(), object.getIdPerson(), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
            for(AnswerChoice answerChoice : object.getAnswerChoices()){
                query = "INSERT INTO ANSWER_ANSWERCHOICE(IDPOLL, IDPERSON, IDANSWERCHOICE) VALUES(?, ?, ?)";
                DatabaseUtil.executeUpdate(query, object.getIdPoll(), object.getIdPerson(), answerChoice.getId());
            }
        }
        return null;
    }

    @Override
    public List<Answer> addAll(List<Answer> objects) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Answer object) {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Answer object) throws SQLException {
        return false;
    }
}
