package com.project42.secretsanta;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project42.secretsanta.business.Mailer;
import com.project42.secretsanta.business.MatchMaker;
import com.project42.secretsanta.business.Provider;
import com.project42.secretsanta.model.Pair;
import com.project42.secretsanta.model.Teamster;

@SpringBootApplication
public class SecretSanta {

	private static final Logger LOGGER = Logger.getLogger(SecretSanta.class.getName());

	public static void main(String[] args) {
		Provider provider = new Provider();
		MatchMaker matchMaker = new MatchMaker();
		Mailer mailer = new Mailer();

		try {

			List<Teamster> teamsters = provider.provide();
			LOGGER.info("Loaded " + teamsters.size() + " teamsters...");

			Set<Pair> matches = matchMaker.match(teamsters);
			LOGGER.info("Matched " + matches.size() + " pairs...");

			mailer.email(matches);
			LOGGER.info("Emails sent!");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Oh, snap! " + e.getMessage());
			e.printStackTrace();
			System.exit(0);
		}
	}
}
