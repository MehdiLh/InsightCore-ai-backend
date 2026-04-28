package com.app.InsightCore_AI;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InsightCoreAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightCoreAiApplication.class, args);
	}

	@Bean
    public CommandLineRunner runner(ChatModel chatModel, ApplicationContext context) {
        return args -> {
            // TODO: Add any initialization logic here
        };
    }
}
