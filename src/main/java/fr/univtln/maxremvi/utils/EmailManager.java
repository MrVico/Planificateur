package fr.univtln.maxremvi.utils;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailManager {

    // The pattern of a valid email address
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /***
     * Determines if the email parameter corresponds to a valid email
     *
     * @param email The email address to test
     * @return true if the parameter if a valid email address, false if not
     */
    public static boolean emailValidator(String email) {
        if (EmailManager.validateWithRegex(email)) {
            try {
                String domain = email.split("@")[1];
                Hashtable env = new Hashtable();
                env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
                DirContext ictx = new InitialDirContext(env);
                Attributes attrs = ictx.getAttributes(domain, new String[]{"MX"});
                Attribute attr = attrs.get("MX");
                if (attr != null)
                    return true;
            } catch (NamingException e) {
                System.out.println(email + " n'est pas un email valide !");
            }
        } else {
            System.out.println(email + " n'est pas bien renseigné !");
        }

        return false;
    }

    /***
     * Determines if the email parameter matches a valid email pattern
     *
     * @param emailStr The email address to test
     * @return true if the parameter matches the email pattern, false if not
     */
    public static boolean validateWithRegex(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
