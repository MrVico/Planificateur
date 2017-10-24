package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.PollDao;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class PollController {
    private PollDao pollDao = null;
    private static PollController pollController = null;

    private PollController() {
        try {
            this.pollDao = DatabaseDao.getPollDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PollController getInstance(){
        if (pollController == null)
            pollController = new PollController();
        return pollController;
    }

    public Poll addPoll(String title, String description, String location, Date closingDate, boolean closed, int promoterID, boolean multipleChoice, boolean hideAnswers, boolean addDates, Poll.pollType pollType) throws SQLException {
        return pollDao.add(new Poll(null, title, description, location, closingDate, closed, promoterID, multipleChoice, hideAnswers, addDates, pollType));
    }

    public boolean updatePoll(Poll p) throws SQLException {
        return pollDao.update(p);
    }


    public int getPollPromoterID(int idPoll){
        return pollDao.getPollPromoterID(idPoll);
    }

    public Poll getPoll(int id) {
        return pollDao.get(id);
    }

    public List<Poll> getPolls() {
        return pollDao.getAll();
    }

    public List<Poll> getVisiblePollsForPerson(Person person) {
        return pollDao.getVisiblePollsForPerson(person);
    }
}
