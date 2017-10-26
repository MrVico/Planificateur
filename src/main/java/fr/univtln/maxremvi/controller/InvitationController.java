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
        this.invitationDao = DatabaseDao.getInvitationDao();
    }

    public static InvitationController getInstance() {
        if (invitationController == null)
            return new InvitationController();
        else
            return invitationController;
    }

    public boolean addAll(List<Invitation> invitations) {
        return invitationDao.addInvitations(invitations);
    }

    public List<Invitation> getAll() throws SQLException {
        return invitationDao.getAll();
    }

    public Invitation getInvitation(int idPoll, int idPerson, int idPersonInviter) {
        Invitation invitation = invitationDao.getInvitation(idPoll, idPerson, idPersonInviter);
        return invitation;
    }

    public boolean wasInvitedToPoll(int pollID, int personID) {
        return invitationDao.wasInvitedToPoll(pollID, personID);
    }

    public boolean setInvitationsAsSeen(int pollID, int personID) {
        return invitationDao.setInvitationsAsSeen(pollID, personID);
    }
}
