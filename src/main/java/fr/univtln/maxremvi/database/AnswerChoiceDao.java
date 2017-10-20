package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnswerChoiceDao extends AbstractDao<AnswerChoice> {

    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new AnswerChoiceDao();
        return instance;
    }

    @Override
    public AnswerChoice get(int id) {
        try {
            String query = "SELECT * FROM ANSWERCHOICE WHERE IDANSWERCHOICE = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, id);
            rs.next();
            AnswerChoice answerChoice = AnswerChoice.fromResultSet(rs);
            rs.close();
            return answerChoice;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AnswerChoice> getPollAnswerChoices(int idPoll){
        try {
            String query = "\n" +
                    "SELECT ac.*, count(a.*) TIMESCHOSEN FROM ANSWER a JOIN ANSWERCHOICE ac ON a.IDANSWERCHOICE = ac.IDANSWERCHOICE " +
                    "WHERE ac.IDPOLL = ? GROUP BY a.IDANSWERCHOICE ORDER BY ac.DATECHOICE ASC";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll);
            List<AnswerChoice> answerChoices = new ArrayList<>();
            while(rs.next()){
                answerChoices.add(new AnswerChoice(rs.getInt("IDANSWERCHOICE"), rs.getTimestamp("CREATIONDATE"),
                        rs.getTimestamp("DATECHOICE"), rs.getInt("IDPOLL"), rs.getString("TIMESCHOSEN")));
            }
            return answerChoices;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<AnswerChoice> getPollAnswerChoicesForPerson(int idPoll, int idPerson){
        try {
            String query = "SELECT ac.* FROM ANSWER a JOIN ANSWERCHOICE ac ON a.IDANSWERCHOICE = ac.IDANSWERCHOICE WHERE a.IDPOLL = ? AND a.IDPERSON = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll, idPerson);
            List<AnswerChoice> answerChoices = new ArrayList<>();
            while(rs.next()){
                answerChoices.add(new AnswerChoice(rs.getInt("IDANSWERCHOICE"), rs.getTimestamp("CREATIONDATE"), rs.getTimestamp("DATECHOICE"), rs.getInt("IDPOLL")));
            }
            return answerChoices;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<AnswerChoice> getAll() {
        return null;
    }

    @Override
    public AnswerChoice add(AnswerChoice object) throws SQLException {
        String query = "INSERT INTO ANSWERCHOICE(DATECHOICE, CREATIONDATE, IDPOLL) VALUES(?, ?, ?)";
        int answerChoiceId = DatabaseUtil.executeInsert(query, TimeManager.timeToSqlFormat(object.getDateChoice()), TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()), object.getIdPoll());
        return ((AnswerChoiceDao) getInstance()).get(answerChoiceId);
    }

    @Override
    public List<AnswerChoice> addAll(List<AnswerChoice> objects) throws SQLException {
        List<AnswerChoice> insertedAnswerChoices = new ArrayList<>();
        for (AnswerChoice answerChoice : objects) {
            insertedAnswerChoices.add(add(answerChoice));
        }
        return insertedAnswerChoices;
    }

    @Override
    public boolean update(AnswerChoice object) {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(AnswerChoice object) throws SQLException {
        return false;
    }
}
