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

import com.project42.secretsanta.model.Pair;

@Component
public class Mailer {

	private static final String SMTP_AUTH = "mail.smtp.auth";
	private static final String SMTP_TLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String SMTP_HOST = "mail.smtp.host";
	private static final String SMTP_PORT = "mail.smtp.port";
	private static final String SMTP_SSL_TRUST = "mail.smtp.ssl.trust";
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

	public void email(Set<Pair> matches) {
		Session session = createSession();
		matches.forEach(match -> sendMessage(match, session));
	}

	private Session createSession() {
		Properties prop = new Properties();
		prop.put(SMTP_AUTH, true);
		prop.put(SMTP_TLS_ENABLE, "true");
		prop.put(SMTP_HOST, host);
		prop.put(SMTP_PORT, port);
		prop.put(SMTP_SSL_TRUST, host);

		return Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
	}

	private void sendMessage(Pair match, Session session) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("secretsanta@fisglobal.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("aleksandar-sasa.serafimovski@fisglobal.com"));
			message.setSubject("Secret Santa");

			String msg = "Tvoj par je: " + match.getTo().getName();

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);

			message.setContent(multipart);

			Transport.send(message);

			LOGGER.info("Email dispatched, from {} to {}", //
					match.getFrom().getName(), //
					match.getTo().getName());
		} catch (Exception e) {
			LOGGER.error("Oh, snap! Email failed:\n\n{}", e.getMessage());
			e.printStackTrace();
		}
	}
}
