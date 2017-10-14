package fr.univtln.maxremvi.Controller;

import com.j256.ormlite.dao.Dao;
import fr.univtln.maxremvi.Database.DatabaseDao;
import fr.univtln.maxremvi.Model.Poll;

import java.sql.SQLException;
import java.util.List;

public class PollController {

    public List<Poll> getPolls(){
        try {
            Dao<Poll, String> pollDao = DatabaseDao.getPollDoa();
            List<Poll> pollList = pollDao.queryBuilder().query();
            System.out.println(pollList);
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
