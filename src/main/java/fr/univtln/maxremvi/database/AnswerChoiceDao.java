package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Answer;
import fr.univtln.maxremvi.model.AnswerChoice;
import fr.univtln.maxremvi.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
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
            String query = "SELECT * FROM ANSWERCHOICE WHERE ID = ?";
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

    @Override
    public List<AnswerChoice> getAll() {
        return null;
    }

    @Override
    public AnswerChoice add(AnswerChoice object) throws SQLException {
        return null;
    }

    @Override
    public List<AnswerChoice> addAll(List<AnswerChoice> objects) throws SQLException {
        return null;
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
