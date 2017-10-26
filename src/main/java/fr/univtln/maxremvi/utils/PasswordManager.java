package fr.univtln.maxremvi.utils;


import java.security.MessageDigest;


public class PasswordManager {
    // The salt to hash the password
    private static final String salt = "r0a959adz9azd590az";

    /***
     * Encrypt a password with SHA-256 algorithm and returns it
     * @param password The password to encrypt
     * @return The crypted password
     */
    public static String encrypt(String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }
}
