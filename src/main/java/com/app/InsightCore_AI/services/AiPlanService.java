package com.app.InsightCore_AI.services;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import com.app.InsightCore_AI.dto.AiQueryPlan;

@Service
public class AiPlanService {
    
    private final ChatClient chatClient;
    private final AiMemoryService memoryService;
    private final PromptService promptService;

    public AiPlanService(ChatClient chatClient, 
                        AiMemoryService memoryService, 
                        PromptService promptService) {
        this.chatClient = chatClient;
        this.memoryService = memoryService;
        this.promptService = promptService;
    }

    public AiQueryPlan extractAndValidate(String sessionId, String userPrompt) {

        String history = memoryService.getRecentHistory(sessionId, 5);

        String template = promptService.loadPrompt("query-plan.txt");

        PromptTemplate promptTemplate = new PromptTemplate(template);

    Prompt prompt = promptTemplate.create(Map.of(
        "history", history,
        "question", userPrompt
    ));

    AiQueryPlan plan = chatClient
        .prompt(prompt)
        .call()
        .entity(AiQueryPlan.class);

System.out.println("PROMPT = " + template);
        return validate(plan);
    }

    private AiQueryPlan validate(AiQueryPlan plan) {

        if (plan == null) plan = new AiQueryPlan();

        if (plan.getMetric() == null) plan.setMetric("amount");

        if (plan.getGroupBy() == null ||
                !List.of("day", "month", "year").contains(plan.getGroupBy())) {
            plan.setGroupBy("month");
        }

        if (plan.getChartType() == null) plan.setChartType("line");

        return plan;
    }
}
