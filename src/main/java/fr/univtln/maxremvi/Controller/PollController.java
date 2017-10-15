package fr.univtln.maxremvi.Controller;

import fr.univtln.maxremvi.Database.AbstractDao;
import fr.univtln.maxremvi.Database.DatabaseDao;
import fr.univtln.maxremvi.Database.PollDao;
import fr.univtln.maxremvi.Model.Person;
import fr.univtln.maxremvi.Model.Poll;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PollController {
    private PollDao pollDao = null;

    public PollController() {
        try {
            this.pollDao = DatabaseDao.getPollDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Poll addPoll(String title, String description, String location, Date closingDate, boolean closed, Person promoter) throws SQLException {
        return pollDao.add(new Poll(title, description, location, closingDate, closed, promoter));
    }

    public List<Poll> getPolls() {
        return pollDao.getAll();
    }
}
