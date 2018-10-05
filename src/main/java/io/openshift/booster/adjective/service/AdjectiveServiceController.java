package io.openshift.booster.adjective.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import io.openshift.booster.adjective.model.Adjective;

@Path("/")
@Component
public class AdjectiveServiceController {

	private List<Adjective> adjectives = new ArrayList<>();

	@GET
	@Path("/adjective")
	@Produces("application/json")
	public Adjective getAdjective() {

		System.out.println(" called" + adjectives.size());

		return adjectives.get(new Random().nextInt(adjectives.size()));
	}

	@PostConstruct
	public void init() {

		try {
			System.out.println("init called");

			Resource resource = new ClassPathResource("adjectives.txt");
			InputStream is = resource.getInputStream();
			if (is != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				reader.lines().forEach(adj -> adjectives.add(new Adjective(adj.trim())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}