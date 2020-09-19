package com.project42.secretsanta.business;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.project42.secretsanta.model.Pair;
import com.project42.secretsanta.model.Teamster;

public class MatchMakerTest {

	private final MatchMaker sut = new MatchMaker();

	@Test
	public void match() throws Exception {
		// Given
		List<Teamster> teamsters = Arrays.asList(//
				new TeamsterBuilder().id(1).build(), //
				new TeamsterBuilder().id(2).build(), //
				new TeamsterBuilder().id(3).build());
		// When
		Set<Pair> matches = sut.match(teamsters);
		// Then
		then(matches)//
				.hasSize(3)//
				.noneMatch(match -> match.getFrom().getId().equals(match.getTo().getId()));
	}
}
