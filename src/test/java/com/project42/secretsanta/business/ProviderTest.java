package com.project42.secretsanta.business;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.project42.secretsanta.model.Teamster;

public class ProviderTest {

	private final Provider sut = new Provider();

	@Test
	void provide() throws Exception {
		// Given
		// When
		List<Teamster> teamsters = sut.provide();
		// Then
		assertFalse(teamsters.isEmpty());
	}
}
