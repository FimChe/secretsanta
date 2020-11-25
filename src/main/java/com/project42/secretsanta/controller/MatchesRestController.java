package com.project42.secretsanta.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project42.secretsanta.business.Mailer;
import com.project42.secretsanta.business.MatchMaker;
import com.project42.secretsanta.business.Provider;
import com.project42.secretsanta.model.Match;
import com.project42.secretsanta.model.Teamster;

@RestController
@RequestMapping(value = "/matches")
public class MatchesRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchesRestController.class);

	private final Provider provider;
	private final MatchMaker matchMaker;
	private final Mailer mailer;

	MatchesRestController(Provider provider, MatchMaker matchMaker, Mailer mailer) {
		this.provider = provider;
		this.matchMaker = matchMaker;
		this.mailer = mailer;
	}

	@GetMapping(value = "/mail")
	public String work() {
		try {

			List<Teamster> teamsters = provider.provide();
			LOGGER.info("Loaded {} teamsters...", teamsters.size());

			Set<Match> matches = matchMaker.match(teamsters);
			LOGGER.info("Matched {} pairs...", matches.size());

			mailer.email(matches);
			LOGGER.info("Emails sent!");

			return "Done! :)";
		} catch (Exception e) {
			LOGGER.error("Oh, snap!\n\n{}", e.getMessage());
			e.printStackTrace();

			return "Failed :(";
		}

	}

	@GetMapping
	public Set<Match> getMatches() {
		List<Teamster> teamsters;
		try {
			teamsters = provider.provide();
			LOGGER.info("Loaded {} teamsters...", teamsters.size());

			Set<Match> matches = matchMaker.match(teamsters);
			LOGGER.info("Matched {} pairs...", matches.size());

			return matches;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
