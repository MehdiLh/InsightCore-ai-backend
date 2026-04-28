package com.app.InsightCore_AI.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.app.InsightCore_AI.dto.AiAnalysisResponse;
import com.app.InsightCore_AI.services.AiOrchestratorService;

@RestController
@RequestMapping("/api/ai")
public class ChatController {

    private final AiOrchestratorService aiOrchestratorService;

    public ChatController(AiOrchestratorService aiOrchestratorService) {
        this.aiOrchestratorService = aiOrchestratorService;
    }

    /**
     * @param sessionId
     * @param message
     * @return
     */
    @PostMapping("/analyze")
    public ResponseEntity<AiAnalysisResponse> analyze(
            @RequestParam String sessionId,
            @RequestBody String message) {

        AiAnalysisResponse response =
                aiOrchestratorService.analyze(sessionId, message);

        return ResponseEntity.ok(response);
    }
}