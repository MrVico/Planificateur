package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Invitation;
import fr.univtln.maxremvi.model.User;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InvitationDao extends AbstractDao<Invitation> {
    /***
     * @return The instance of the current implementation of this InvitationDao
     */
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

    /***
     * @return All the unseen Invitations for the connected user
     */
    @Override
    public List<Invitation> getAll() {
        try {
            String query = "Select * from Invitation where IDPERSON = ? and SEEN = false";
            ResultSet rs = DatabaseUtil.executeQuery(query, User.getUser().getID());
            List<Invitation> invitationList = new ArrayList<>();
            while (rs.next()) {
                invitationList.add(new Invitation(rs.getInt("IDPOLL"), rs.getInt("IDPERSON"), rs.getInt("IDPERSONINVITER"), rs.getBoolean("SEEN")));
            }
            return invitationList;
        } catch (SQLException e) {
            System.out.println("Invitation getAll : " + e.getMessage());
            return null;
        }
    }

    /***
     * Stores a List of Invitations into the database
     *
     * @param invitations The List of Invitations to store
     * @return true if the stores happened successfully, false if not
     */
    public boolean addInvitations(List<Invitation> invitations) {
        for (Invitation invitation : invitations) {
            if (!addInvitation(invitation))
                return false;
        }
        return true;
    }

    /***
     * Store an Invitation into the database
     *
     * @param invitation The Invitation to store
     * @return true if the stores happened successfully, false if not
     */
    public boolean addInvitation(Invitation invitation) {
        try {
            String query = "INSERT INTO INVITATION VALUES(?, ?, ?, ?, ?)";
            DatabaseUtil.executeUpdate(query, invitation.getPollID(), invitation.getPersonID(), invitation.getSenderID(), false, TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
            return true;
        } catch (SQLException e) {
            System.out.println("Invitation addInvitation : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Invitation add(Invitation object) {
        return null;
    }

    @Override
    public List<Invitation> addAll(List<Invitation> objects) {
        return null;
    }

    /***
     * Retreive the Invitation for the given Poll, Person and Inviter
     *
     * @param idPoll
     * @param idPerson
     * @param idPersonInviter
     * @return
     */
    public Invitation getInvitation(int idPoll, int idPerson, int idPersonInviter) {
        try {
            String query = "SELECT * FROM INVITATION WHERE IDPOLL= ? AND IDPERSON = ? AND IDPERSONINVITER = ?";
            ResultSet rs = DatabaseUtil.executeQuery(query, idPoll, idPerson, idPersonInviter);
            rs.next();
            Invitation invitation = Invitation.fromResultSet(rs);
            return invitation;
        } catch (SQLException e) {
            System.out.println("Invitation getInvitation : " + e.getMessage());
            return null;
        }
    }

    /***
     *
     *
     * @param pollID
     * @param personID
     * @return
     */
    public boolean wasInvitedToPoll(int pollID, int personID) {
        try {
            String query = "SELECT * FROM INVITATION WHERE IDPOLL = ? AND IDPERSON = ? AND SEEN=FALSE";
            ResultSet rs = DatabaseUtil.executeQuery(query, pollID, personID);
            if (rs.next())
                return true;
            return false;
        } catch (SQLException e) {
            System.out.println("Invitation wasInvitedToPoll : " + e.getMessage());
            return false;
        }
    }

    /***
     *
     *
     * @param pollID
     * @param personID
     * @return
     */
    public boolean setInvitationsAsSeen(int pollID, int personID) {
        try {
            String query = "UPDATE INVITATION SET SEEN=TRUE WHERE IDPOLL = ? AND IDPERSON = ?";
            DatabaseUtil.executeUpdate(query, pollID, personID);
            return true;
        } catch (SQLException e) {
            System.out.println("Invitation setInvitationsAsSeen : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Invitation object) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        return false;
    }

    @Override
    public boolean remove(Invitation object) {
        return false;
    }
}
