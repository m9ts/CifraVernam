package com.segi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SegiApplication {

	private final Environment environment;

	public SegiApplication(Environment environment) {
		this.environment = environment;
	}

	public static void main(String[] args) {
		SpringApplication.run(SegiApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void aoIniciar() {
		String porta = environment.getProperty("local.server.port");
		System.out.println("API rodando na URL: http://localhost:" + porta + "/swagger-ui/index.html");
	}

}
