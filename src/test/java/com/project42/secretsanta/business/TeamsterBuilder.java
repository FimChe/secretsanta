package com.project42.secretsanta.business;

import com.project42.secretsanta.model.Teamster;

public class TeamsterBuilder {

	private Teamster teamster;

	public TeamsterBuilder() {
		teamster = new Teamster();
	}

	public TeamsterBuilder id(Integer id) {
		teamster.setId(id);
		return this;
	}

	public TeamsterBuilder firstName(String firstName) {
		teamster.setFirstName(firstName);
		return this;
	}

	public TeamsterBuilder lastName(String lastName) {
		teamster.setLastName(lastName);
		return this;
	}

	public TeamsterBuilder email(String email) {
		teamster.setEmail(email);
		return this;
	}

	public Teamster build() {
		return teamster;
	}
}
