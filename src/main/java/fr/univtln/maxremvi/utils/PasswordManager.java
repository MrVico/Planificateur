package fr.univtln.maxremvi.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


public class PasswordManager {

    private static final String salt = "r0a959adz9azd590az";

    public static String encrypt(String password){
        String encode="" ;
        for (int i=0; i<password.length();i++)  {
            int c=password.charAt(i)^48;
            encode=encode+(char)c;
        }
        return encode;

        /*StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        return encryptor.encrypt(password);*/

    }

    public static String decrypt(String password){
        String decrypt="";
        for (int i=0; i<password.length();i++)  {
            int c=password.charAt(i)^48;
            decrypt=decrypt+(char)c;
        }
        return decrypt;

       /*StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        return encryptor.decrypt(password);*/
    }
}
