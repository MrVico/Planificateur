package fr.univtln.maxremvi.Controller;

import com.j256.ormlite.dao.Dao;
import fr.univtln.maxremvi.Database.DatabaseDao;
import fr.univtln.maxremvi.Database.DatabaseUtil;
import fr.univtln.maxremvi.Model.Person;
import fr.univtln.maxremvi.Model.Poll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by remi on 14/10/2017.
 */
public class PollController {
    public PollController() {

    }

    public Poll addPoll(String title, String description, String location, Date closingDate, boolean closed, Person promoter) throws SQLException, IOException {
        Dao<Poll, String> pollDao = DatabaseDao.getPollDao();
        Poll poll = new Poll(title, description, location, closingDate, closed, promoter);

        if (poll != null)
            pollDao.create(poll);

        DatabaseUtil.closeConnection();

        return poll;
    }
}
