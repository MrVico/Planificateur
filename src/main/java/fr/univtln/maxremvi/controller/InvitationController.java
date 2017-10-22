package fr.univtln.maxremvi.controller;

import com.j256.ormlite.stmt.query.In;
import fr.univtln.maxremvi.database.DatabaseDao;
import fr.univtln.maxremvi.database.InvitationDao;
import fr.univtln.maxremvi.model.Invitation;

import java.sql.SQLException;
import java.util.List;

public class InvitationController {
    private InvitationDao invitationDao = null;
    private static InvitationController invitationController = null;

    private InvitationController() {
        try {
            this.invitationDao = DatabaseDao.getInvitationDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static InvitationController getInstance(){
        if(invitationController == null)
            return new InvitationController();
        else
            return invitationController;
    }

    public boolean addAll(List<Invitation> invitations) throws SQLException{
        return invitationDao.addInvitations(invitations);
    }

    public List<Invitation> getAll()throws SQLException{
        return invitationDao.getAll();
    }

    public Invitation getInvitation(int idPoll,int idPerson, int idPersonInviter) throws SQLException
    {
        Invitation invitation=invitationDao.getInvitation(idPoll,idPerson,idPersonInviter);
        return invitation;
    }

    public void setInvitationSeenTrue(Invitation invitation)throws SQLException
    {
        invitationDao.setInvitationTrue(invitation);
    }
}
