package com.app.InsightCore_AI.services;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.app.InsightCore_AI.dto.AiAnalysisResponse;

import java.util.Map;

@Service
public class AiAnalysisService {

    private final ChatClient chatClient;
    private final PromptService promptService;

    public AiAnalysisService(ChatClient chatClient, PromptService promptService) {
        this.chatClient = chatClient;
        this.promptService = promptService;
    }

    public AiAnalysisResponse generate(String question, String facts) {

        String template = promptService.loadPrompt("analysis.txt");

       PromptTemplate promptTemplate = new PromptTemplate(template);

        Prompt prompt = promptTemplate.create(Map.of(
            "question", question,
            "facts", facts
        ));
System.out.println("PROMPT = " + template);
        return chatClient
                .prompt(prompt)
                .call()
                .entity(AiAnalysisResponse.class);
    }
}   