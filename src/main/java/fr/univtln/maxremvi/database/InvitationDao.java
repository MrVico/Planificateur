package fr.univtln.maxremvi.database;

import fr.univtln.maxremvi.model.Invitation;
import fr.univtln.maxremvi.utils.TimeManager;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class InvitationDao extends AbstractDao {
    @Override
    public AbstractDao getInstance() {
        if (instance == null)
            instance = new InvitationDao();
        return instance;
    }

    @Override
    public Object get(int id) {
        return null;
    }

    @Override
    public List getAll() {
        return null;
    }

    @Override
    public Object add(Object object) throws SQLException {
        return null;
    }

    public boolean addInvitations(List<Invitation> invitations) throws SQLException {
        for(Invitation invitation : invitations){
            if(!add(invitation))
                return false;
        }
        return true;
    }

    public boolean add(Invitation invitation) throws SQLException {
        String query = "INSERT INTO INVITATION VALUES(?, ?, ?, ?, ?)";
        DatabaseUtil.executeUpdate(query, invitation.getIdPoll(), invitation.getIdPerson(), invitation.getIdSender(), false, TimeManager.timeToSqlFormat(Calendar.getInstance().getTime()));
        return true;
    }

    @Override
    public List addAll(List objects) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Object object) {
        return false;
    }

    @Override
    public boolean remove(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Object object) throws SQLException {
        return false;
    }
}
