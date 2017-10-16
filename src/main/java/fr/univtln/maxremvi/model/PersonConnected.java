package fr.univtln.maxremvi.model;

public class PersonConnected {
    private static String login=null;

    public static boolean isLog(){
        if(login!=null)
            return true;
        else
            return false;
    }

    public static void setLogin(String log)
    {
        login=log;
    }

}
