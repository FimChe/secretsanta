package com.project42.secretsanta.business;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project42.secretsanta.model.Teamster;

@Component
public class Provider {

	public List<Teamster> provide() throws Exception {
		return new ObjectMapper().readValue(//
				new File("D:/Develop/Secret Santa/teamsters.json"), //
				new TypeReference<List<Teamster>>() {
				});
	}
}
