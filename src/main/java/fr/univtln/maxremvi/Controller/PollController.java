package fr.univtln.maxremvi.controller;

import fr.univtln.maxremvi.model.Person;
import fr.univtln.maxremvi.model.Poll;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PollController {

    public Poll addPoll(String title, String description, String location, Date closingDate, boolean closed, Person promoter){
        return new Poll(title, description, location, closingDate, closed, promoter);
    }

    public List<Poll> getPolls(){
        try {
            //faudrait mettre la connexion + statement dans un controller parent
            Connection conn = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/planificateur","sa","");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM POLL";
            ResultSet rs = stmt.executeQuery(query);
            List<Poll> pollList = new ArrayList<>();
            Person pers = new Person("Login", "Password", "email@gmail.com", null, null);
            while(rs.next()){
                //System.out.println(rs.getString("TITLE")+" "+rs.getString("DESCRIPTION")+" "+rs.getString("LOCATION")+" "+rs.getDate("CLOSINGDATE")+" "+rs.getString("CLOSED"));
                pollList.add(addPoll(rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getString("LOCATION"), rs.getDate("CLOSINGDATE"),
                        rs.getBoolean("CLOSED"), pers));
            }
            rs.close();
            stmt.close();
            conn.close();
            return pollList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
