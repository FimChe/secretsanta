package com.project42.secretsanta.business;

import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Component;

import com.project42.secretsanta.model.Pair;

@Component
public class Mailer {

	public void email(Set<Pair> matches) {
		Session session = createSession();
		matches.forEach(match -> sendMessage(match, session));
	}

	private Session createSession() {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp-relay.sendinblue.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp-relay.sendinblue.com");

		return Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("a.serafimovski@yahoo.com", "Htd0B4LOqWSamMYI");
			}
		});
	}

	private void sendMessage(Pair match, Session session) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("santa@northpole.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("a.serafimovksi@yahoo.com"));
			message.setSubject("Secret Santa");

			String msg = "Tvoj par je: " + match.getTo().getFirstName() + " " + match.getTo().getLastName();

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
