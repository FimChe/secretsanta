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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.project42.secretsanta.model.Match;
import com.project42.secretsanta.model.Teamster;

@Component
public class Mailer {

	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String SMTP_PORT = "mail.smtp.port";
	private static final String SMTP_AUTH = "mail.smtp.auth";
	private static final String SMTP_SSL_ENABLE = "mail.smtp.ssl.enable";
	private static final String USERNAME = "mail.username";
	private static final String PASSWORD = "mail.password";

	private static final Logger LOGGER = LoggerFactory.getLogger(Mailer.class);

	private final String host;
	private final Integer port;
	private final String username;
	private final String password;

	Mailer(//
			@Value("${" + SMTP_HOST + "}") String host, //
			@Value("${" + SMTP_PORT + "}") Integer port, //
			@Value("${" + USERNAME + "}") String username, //
			@Value("${" + PASSWORD + "}") String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	public void email(Set<Match> matches) throws Exception {
		Session session = createSession();
		for (Match match : matches) {
			sendMessage(match, session);
		}
	}

	private Session createSession() {
		Properties prop = new Properties();
		prop.put(SMTP_HOST, host);
		prop.put(SMTP_PORT, port);
		prop.put(SMTP_AUTH, "true");
		prop.put(SMTP_SSL_ENABLE, "true");

		Session session = Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
//		session.setDebug(true);

		return session;
	}

	private void sendMessage(Match match, Session session) throws Exception {
		try {
			Message message = new MimeMessage(session);
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(match.getFrom().getEmail()));
			message.setSubject("Secret Santa");

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(generateContent(match.getTo()), "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);

			LOGGER.info("Email dispatched, from {} to {}", //
					match.getFrom().getName(), //
					match.getTo().getName());
		} catch (Exception e) {
			LOGGER.error("Oh, snap! Email failed:\n\n{}", e.getMessage());
			throw e;
		}
	}

	String generateContent(Teamster receiver) {
		StringBuilder content = new StringBuilder("Tvoj par je: ")//
				.append(receiver.getName());

		if (receiver.getAddress() != null) {
			content.append("<br><br>")//
					.append("Adresa za isporuku: ")//
					.append(receiver.getAddress())//
					.append("<br>")//
					.append("Broj telefona (za PostExpress): ")//
					.append(receiver.getPhone())//
					.append("<br><br>")
					.append("Pokloni se otvaraju na Teams pozivu 25.12. u 12:30h (stići će link posebno).");
		} else {
			content.append("<br><br>")//
					.append("Pokloni se razmenjuju 25.12. u 12:30h u parkiću iza FIS zgrade.")//
					.append("<br>")//
					.append("Ponesi masku, da te Ika ne sanja..");
		}

		content.append("<br><br>")//
				.append("XOXO,")//
				.append("<br>")//
				.append("- Santa");

		return content.toString();
	}
}
