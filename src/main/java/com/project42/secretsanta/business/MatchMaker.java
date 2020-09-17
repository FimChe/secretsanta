package com.project42.secretsanta.business;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.project42.secretsanta.model.Pair;
import com.project42.secretsanta.model.Teamster;

@Component
public class MatchMaker {

	public Set<Pair> match(List<Teamster> teamsters) {
		List<Teamster> inbound = new ArrayList<>(teamsters);
		List<Teamster> outbound = new ArrayList<>(teamsters);

		Set<Pair> matches = new HashSet<>();
		Random generator = new Random();

		while (!inbound.isEmpty() && !outbound.isEmpty()) {
			int inboundIndex = generator.nextInt(inbound.size());
			int outboundIndex = generator.nextInt(outbound.size());

			if (!inbound.get(inboundIndex).getId().equals(outbound.get(outboundIndex).getId())) {
				matches.add(new Pair(inbound.remove(inboundIndex), outbound.remove(outboundIndex)));
			}
		}

		return matches;
	}
}
