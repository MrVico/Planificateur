package fr.univtln.maxremvi.utils;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class PasswordManager {

    private static final String salt = "r0a959adz9azd590az";

    public static String encrypt(String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        return encryptor.encrypt(password);
    }

    public static String decrypt(String password){
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(salt);
        return encryptor.decrypt(password);
    }
}
