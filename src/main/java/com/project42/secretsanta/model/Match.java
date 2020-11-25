package com.project42.secretsanta.model;

public class Match {

	private final Teamster from;
	private final Teamster to;

	public Match(Teamster from, Teamster to) {
		this.from = from;
		this.to = to;
	}

	public Teamster getFrom() {
		return from;
	}

	public Teamster getTo() {
		return to;
	}

}
