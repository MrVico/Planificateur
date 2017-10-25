package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Invitation;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvitationDao extends AbstractDao<Invitation>{
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new InvitationDao();
        return instance;
    }

    @Override
    public Invitation get(int id) {
        return null;
    }

    @Override
    public List<Invitation> getAll()throws SQLException {
        String query="Select * from Invitation where IDPERSON = ? and SEEN = false";
        ResultSet rs=DatabaseUtil.executeQuery(query, User.getUser().getID());
        List<Invitation> invitationList=new ArrayList<>();
        while (rs.next())
        {
            invitationList.add(new Invitation(rs.getInt("IDPOLL"),rs.getInt("IDPERSON"),rs.getInt("IDPERSONINVITER"),rs.getBoolean("SEEN")));
        }


        return invitationList;
    }

    public boolean addInvitations(List<Invitation> invitations) throws SQLException {
        for(Invitation invitation : invitations){
            if(!addInvitation(invitation))
                return false;
        }
        return true;
    }

    public boolean addInvitation(Invitation invitation) throws SQLException {
        String query = "INSERT INTO INVITATION VALUES(?, ?, ?, ?, ?)";
        DatabaseUtil.executeUpdate(query, invitation.getPollID(), invitation.getPersonID(), invitation.getSenderID(), false, TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
        return true;
    }

    @Override
    public Invitation add(Invitation object) throws SQLException {
        return null;
    }

    @Override
    public List<Invitation> addAll(List<Invitation> objects) throws SQLException {
        return null;
    }

    public Invitation getInvitation(int idPoll,int idPerson, int idPersonInviter) throws SQLException
    {
        String query="SELECT * FROM INVITATION WHERE IDPOLL= ? AND IDPERSON = ? AND IDPERSONINVITER = ?";
        ResultSet rs=DatabaseUtil.executeQuery(query,idPoll,idPerson,idPersonInviter);
        rs.next();
        Invitation invitation=Invitation.fromResultSet(rs);
        return invitation;
    }

    public boolean wasInvitedToPoll(int pollID, int personID) throws SQLException {
        String query = "SELECT * FROM INVITATION WHERE IDPOLL = ? AND IDPERSON = ? AND SEEN=FALSE";
        ResultSet rs = DatabaseUtil.executeQuery(query, pollID, personID);
        if(rs.next())
            return true;
        return false;
    }

    public void setInvitationsAsSeen(int pollID, int personID) throws SQLException {
        String query="UPDATE INVITATION SET SEEN=TRUE WHERE IDPOLL = ? AND IDPERSON = ?";
        DatabaseUtil.executeUpdate(query, pollID, personID);
    }

    @Override
    public boolean update(Invitation object) {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Invitation object) throws SQLException {
        return false;
    }
}
