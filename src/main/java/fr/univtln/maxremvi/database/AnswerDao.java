package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Answer;
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

    public boolean answerExists(Answer answer){
        try {
            String query = "SELECT * FROM ANSWER WHERE IDPOLL = ? AND IDPERSON = ? AND IDANSWERCHOICE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, answer.getPollID(), answer.getPersonID(), answer.getAnswerChoiceID());
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
        if(!answerExists(object)){
            String query = "INSERT INTO ANSWER(IDPOLL, IDPERSON, IDANSWERCHOICE, CREATIONDATE) VALUES(?, ?, ?, ?)";
            DatabaseUtil.executeUpdate(query, object.getPollID(), object.getPersonID(), object.getAnswerChoiceID(), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
        }
        return null;
    }

    @Override
    public List<Answer> addAll(List<Answer> objects) throws SQLException {
        for(Answer answer : objects){
            add(answer);
        }
        // tous les returns sont inutiles IMO
        return null;
    }

    public boolean delete(int idPoll, int idPerson, Integer id){
        try {
            String query = "DELETE FROM ANSWER WHERE IDPOLL = ? AND IDPERSON = ? AND IDANSWERCHOICE = ?";
            DatabaseUtil.executeUpdate(query, idPoll, idPerson, id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteAll(int idPoll, int idPerson, List<Integer> answerIDs) {
        for(Integer id : answerIDs){
            if(!delete(idPoll, idPerson, id))
                return false;
        }
        return true;
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
