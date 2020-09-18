package com.project42.secretsanta.business;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Multipart;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(Mailer.class);

	private final String host;
	private final Integer port;

	Mailer(@Value("${" + SMTP_HOST + "}") String host, @Value("${" + SMTP_PORT + "}") Integer port) {
		this.host = host;
		this.port = port;
	}

	public void email(Set<Pair> matches) {
		Session session = createSession();
		matches.forEach(match -> sendMessage(match, session));
	}

	private Session createSession() {
		Properties prop = new Properties();
		prop.put(SMTP_AUTH, false);
		prop.put(SMTP_TLS_ENABLE, "false");
		prop.put(SMTP_HOST, host);
		prop.put(SMTP_PORT, port);

		return Session.getDefaultInstance(prop);
	}

	private void sendMessage(Pair match, Session session) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("santa@northpole.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(match.getTo().getEmail()));
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
