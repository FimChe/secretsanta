package com.project42.secretsanta.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.project42.secretsanta.model.Match;
import com.project42.secretsanta.model.Teamster;

@Component
public class MatchMaker {

	public Set<Match> match(List<Teamster> teamsters) {
		Map<Boolean, List<Teamster>> circles = teamsters.stream()
				.collect(Collectors.partitioningBy(teamster -> teamster.getAddress() != null));

		List<Teamster> delivery = circles.get(Boolean.TRUE);
		List<Teamster> meetup = circles.get(Boolean.FALSE);

		return Stream.concat(doMatch(delivery).stream(), doMatch(meetup).stream()).collect(Collectors.toSet());
	}

	private Set<Match> doMatch(List<Teamster> teamsters) {
		List<Teamster> inbound = new ArrayList<>(teamsters);
		List<Teamster> outbound = new ArrayList<>(teamsters);

		Set<Match> matches = new HashSet<>();
		Random generator = new Random();

		while (!inbound.isEmpty() && !outbound.isEmpty()) {
			int inboundIndex = generator.nextInt(inbound.size());
			int outboundIndex = generator.nextInt(outbound.size());

			if (!inbound.get(inboundIndex).getId().equals(outbound.get(outboundIndex).getId())) {
				matches.add(new Match(inbound.remove(inboundIndex), outbound.remove(outboundIndex)));
			}
		}

		return matches;
	}
}
