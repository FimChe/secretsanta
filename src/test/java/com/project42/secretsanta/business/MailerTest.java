package com.project42.secretsanta.business;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.Test;

import com.project42.secretsanta.model.Teamster;

public class MailerTest {

	private final Mailer sut = new Mailer(null, null, null, null);

	@Test
	public void getContent() throws Exception {
		// Given
		Teamster deliverer = new TeamsterBuilder()//
				.name("Pera Perić")//
				.address("Neka adresa, BG")//
				.phone("063/123-4567")//
				.build();
		Teamster meetuper = new TeamsterBuilder()//
				.name("Mika Mikić")//
				.build();
		// When
		String delivererContent = sut.generateContent(deliverer);
		String meetuperContent = sut.generateContent(meetuper);
		// Then
		then(delivererContent)//
				.contains(deliverer.getName())//
				.contains(deliverer.getAddress())//
				.contains(deliverer.getPhone());
		then(meetuperContent)//
				.contains(meetuper.getName());
	}
}
