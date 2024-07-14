package edu.icet.controller.user;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.*;

public class EmailController {
    private static EmailController instance;

    public static EmailController getInstance() {
        if (instance == null) {
            return instance = new EmailController();
        }
        return instance;
    }


    public  String sendMail(String email) {

        // Email credentials
        final String from = ""; // replace with your email
        final String username = ""; // replace with your email
        final String password = ""; // replace with your password

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create a Session object to represent a mail session with the specified properties.
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); // replace with recipient's email

            // Set Subject: header field
            message.setSubject("Test Subject");

            String otp = generateOTP();
            // Now set the actual message
            message.setText("This is your forgot Code : "+otp);

            // Send message
            Transport.send(message);

            return otp;

        } catch (MessagingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String generateOTP() {

        int randomPin   = (int) (Math.random()*9000)+1000;
        String otp  = String.valueOf(randomPin);
        return otp;
    }

}
