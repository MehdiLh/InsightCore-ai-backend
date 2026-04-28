package com.app.InsightCore_AI.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.app.InsightCore_AI.model.Chat;
import com.app.InsightCore_AI.enums.MessageRole;
import com.app.InsightCore_AI.repository.ChatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiMemoryService {

    private final ChatRepository chatRepository;

    public AiMemoryService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void saveUserMessage(String sessionId, String content) {
        save(sessionId, MessageRole.USER, content);
    }

    public void saveAssistantMessage(String sessionId, String content) {
        save(sessionId, MessageRole.ASSISTANT, content);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void save(String sessionId, MessageRole role, String content) {

        Chat chat = new Chat();
        chat.setSessionId(sessionId);
        chat.setRole(role);
        chat.setContent(content);
        chat.setCreatedAt(LocalDateTime.now());

        chatRepository.saveAndFlush(chat);
    }

    public String getRecentHistory(String sessionId, int max) {

        List<Chat> history =
                chatRepository.findBySessionIdOrderByCreatedAtAsc(sessionId);

        if (history.isEmpty()) return "(no history)";

        return history.stream()
                .skip(Math.max(0, history.size() - max))
                .map(h -> h.getRole() + ": " + h.getContent())
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
    }
}