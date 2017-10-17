package fr.univtln.maxremvi.model;

import fr.univtln.maxremvi.controller.PersonController;

public class User {
    private static Person user = null;

    public static boolean isLogged(){
        if(user!=null)
            return true;
        else
            return false;
    }

    public static Person getUser(){
        return user;
    }

    public static void setUser(Person person)
    {
        user = person;
    }

}
