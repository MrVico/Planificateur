package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;
import fr.univtln.maxremvi.database.AbstractDao;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.PollDao;

import java.sql.*;
import java.util.ArrayList;
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

    public Poll addPoll(String title, String description, String location, Date closingDate, boolean closed, Person promoter, boolean multipleChoice, boolean hideAnswers, boolean addDates, Poll.type pollType) throws SQLException {
        return pollDao.add(new Poll(null, title, description, location, closingDate, closed, promoter, multipleChoice, hideAnswers, addDates, pollType));
    }

    public Poll getPoll(int id) {
        return pollDao.get(id);
    }

    public List<Poll> getPolls() {
        return pollDao.getAll();
    }
}
