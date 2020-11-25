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

	public TeamsterBuilder name(String name) {
		teamster.setName(name);
		return this;
	}

	public TeamsterBuilder email(String email) {
		teamster.setEmail(email);
		return this;
	}

	public TeamsterBuilder address(String address) {
		teamster.setAddress(address);
		return this;
	}

	public TeamsterBuilder phone(String phone) {
		teamster.setPhone(phone);
		return this;
	}

	public Teamster build() {
		return teamster;
	}
}
