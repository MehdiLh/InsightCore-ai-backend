package com.app.InsightCore_AI.services;

import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Service
public class PromptService {

    public String loadPrompt(String name) {
        try {
            Resource resource = new ClassPathResource("prompts/" + name);
            return new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load prompt", e);
        }
    }

   
}