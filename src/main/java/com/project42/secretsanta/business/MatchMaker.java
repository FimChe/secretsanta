package com.project42.secretsanta.business;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
		if (teamsters.isEmpty()) {
			return Collections.emptySet();
		}

		Collections.shuffle(teamsters);

		Set<Match> matches = new HashSet<>();
		IntStream.range(0, teamsters.size() - 1)//
				.forEach(i -> matches.add(new Match(teamsters.get(i), teamsters.get(i + 1))));
		matches.add(new Match(teamsters.get(teamsters.size() - 1), teamsters.get(0)));

		return matches;
	}
}
