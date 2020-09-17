package com.project42.secretsanta.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertEquals(matches.size(), 3);
		assertTrue(matches.stream().noneMatch(pair -> pair.getFrom().getId().equals(pair.getTo().getId())));
	}
}
