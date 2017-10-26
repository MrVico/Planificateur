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

    public Poll addPoll(String title, String description, String location, boolean closed, int promoterID, boolean multipleChoice, boolean hideAnswers, boolean addDates, Poll.pollType pollType) {
        return pollDao.add(new Poll(null, title, description, location, closed, promoterID, multipleChoice, hideAnswers, addDates, pollType, null, null));
    }

    public boolean updatePoll(Poll p) {
        return pollDao.update(p);
    }

    public int getPollPromoterID(int idPoll){
        return pollDao.getPollPromoterID(idPoll);
    }

    public int getMaxCountAnswer(int idPoll) { return pollDao.getMaxCountAnswer(idPoll); }

    public Poll getPoll(int id) {
        return pollDao.get(id);
    }

    public List<Poll> getPolls() {
        return pollDao.getAll();
    }

    public List<Poll> getVisiblePollsForPerson(Person person) {
        return pollDao.getVisiblePollsForPerson(person);
    }

    public boolean deletePoll(int id) {
        return pollDao.remove(id);
    }

    public boolean closePoll(boolean bool, int pollID) {
        return pollDao.close(bool, pollID);
    }

    public boolean setFinalDate(int pollID, Date finalDate) {
        return pollDao.setFinalDate(pollID, finalDate);
    }
}
