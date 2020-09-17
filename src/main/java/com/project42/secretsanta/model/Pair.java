package com.project42.secretsanta.model;

public class Pair {

	private final Teamster from;
	private final Teamster to;

	public Pair(Teamster from, Teamster to) {
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
