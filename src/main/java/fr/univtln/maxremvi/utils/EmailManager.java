package fr.univtln.maxremvi.utils;

import fr.univtln.maxremvi.model.Person;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
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
        // Recipient's email ID needs to be mentioned.
        String to = "zarakikov67@gmail.com";

        // Sender's email ID needs to be mentioned
        String from = "Planificateur_do_not_answer@gmail.com";

        // Assuming you are sending email from localhost
        String host = "localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Testing email sender!");

            // Now set the actual message
            message.setText("Hey, you got an email from me! Congrats!");

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
