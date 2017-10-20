package fr.univtln.maxremvi.controller;

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
}