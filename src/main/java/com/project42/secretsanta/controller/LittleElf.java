package com.project42.secretsanta.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project42.secretsanta.business.Mailer;
import com.project42.secretsanta.business.MatchMaker;
import com.project42.secretsanta.business.Provider;
import com.project42.secretsanta.model.Pair;
import com.project42.secretsanta.model.Teamster;

@RestController
public class LittleElf {

	private static final Logger LOGGER = LoggerFactory.getLogger(LittleElf.class);

	private final Provider provider;
	private final MatchMaker matchMaker;
	private final Mailer mailer;

	LittleElf(Provider provider, MatchMaker matchMaker, Mailer mailer) {
		this.provider = provider;
		this.matchMaker = matchMaker;
		this.mailer = mailer;
	}

	@RequestMapping(value = "/match-make", method = RequestMethod.GET)
	public String work() {
		try {

			List<Teamster> teamsters = provider.provide();
			LOGGER.info("Loaded {} teamsters...", teamsters.size());

			Set<Pair> matches = matchMaker.match(teamsters);
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
}
