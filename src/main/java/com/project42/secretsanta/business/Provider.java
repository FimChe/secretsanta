package com.project42.secretsanta.business;

import java.io.File;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project42.secretsanta.model.Teamster;

@Component
public class Provider {

	private final ObjectMapper objectMapper;
	private final String path;

	Provider(ObjectMapper objectMapper, @Value("${teamsters.path}") String path) {
		this.objectMapper = objectMapper;
		this.path = path;
	}

	public List<Teamster> provide() throws Exception {
		List<Teamster> teamsters = objectMapper.readValue(//
				new File(path), //
				new TypeReference<List<Teamster>>() {
				});

		IntStream.range(0, teamsters.size())//
				.forEach(index -> teamsters.get(index).setId(index));

		return teamsters;
	}
}
