package fr.univtln.maxremvi.utils;

import fr.univtln.maxremvi.model.Person;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailManager {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean emailValidator(String email) {
        if(EmailManager.validateWithRegex(email)){
            try{
                String domain = email.split("@")[1];
                Hashtable env = new Hashtable();
                env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
                DirContext ictx = new InitialDirContext(env);
                Attributes attrs = ictx.getAttributes( domain, new String[] { "MX" });
                Attribute attr = attrs.get("MX");
                if(attr != null)
                    return true;
            }
            catch (NamingException e){
                System.out.println(email+" n'est pas un email valide !");
            }
        }
        else{
            System.out.println(email+" n'est pas bien renseigné !");
        }

        return false;
    }

    public static boolean validateWithRegex(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    public static void sendEmails(List<Person> receivers){
        final String username = "email";
        final String password = "password";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from_email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("to_email@hotmail.fr"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
