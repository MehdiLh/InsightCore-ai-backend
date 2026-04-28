package com.app.InsightCore_AI.services;

import org.springframework.stereotype.Service;

import com.app.InsightCore_AI.dto.AiAnalysisResponse;
import com.app.InsightCore_AI.dto.AiQueryPlan;
import com.app.InsightCore_AI.dto.QueryResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service    
public class AiOrchestratorService {

    private final AiPlanService planService;
    private final AiDataService dataService;
    private final AiAnalysisService analysisService;
    private final AiMemoryService memoryService;
    private final ObjectMapper objectMapper;

    public AiOrchestratorService(AiPlanService planService,
                             AiDataService dataService,
                             AiAnalysisService analysisService,
                             AiMemoryService memoryService,
                             ObjectMapper objectMapper) {
        this.planService = planService;
        this.dataService = dataService;
        this.analysisService = analysisService;
        this.memoryService = memoryService;
        this.objectMapper = objectMapper;
    }

    public AiAnalysisResponse analyze(String sessionId, String userPrompt) {

        memoryService.saveUserMessage(sessionId, userPrompt);

        // 1. Plan
        AiQueryPlan plan = planService.extractAndValidate(sessionId, userPrompt);

        // 2. Data
        QueryResult result = dataService.fetch(plan);

        // 3. Facts
        String facts = dataService.buildFacts(plan, result);

        // 4. AI Analysis
        AiAnalysisResponse response =
                analysisService.generate(userPrompt, facts);

        response.setChartData(result.chartData());

        try {
            memoryService.saveAssistantMessage(sessionId, objectMapper.writeValueAsString(response));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize AI response", e);
        }

        return response;
    }
}