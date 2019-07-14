package com.src.project_cartographer_admin_server.utils;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Properties;

public class EmailHelper {
  public static boolean isValidEmailAddress(String email) {
    try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
    } catch (AddressException ex) {
      return false;
    }
    return true;
  }

  public static void sendEmail(String email, String subject, String text) {
    String to = email;
    String from = "admin@cartographer.online";
    String host = "localhost";
    Properties properties = System.getProperties();
    properties.setProperty("mail.smtp.host", host);
    Session session = Session.getDefaultInstance(properties);
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from));
      message.setReplyTo((Address[]) Arrays.asList(new InternetAddress(from)).toArray());
      message.setHeader("MIME-version", "1.0");
      message.setContent(text, "text/html; charset=ISO-8859-1");
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(subject);
      Transport.send(message);
    } catch (MessagingException e) {
      throw new RuntimeException("Error sending email to " + email + ", msg = " + e.getMessage(), e);
    }
  }
}
