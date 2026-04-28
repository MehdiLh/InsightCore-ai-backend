package com.app.InsightCore_AI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.InsightCore_AI.model.Chat;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{
    // Fetches the latest messages from the same session to give the AI ​​context
    List<Chat> findBySessionIdOrderByCreatedAtAsc(String sessionId);
}
