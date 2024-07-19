package com.vincennlin.aiservice.service.impl;

import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private final OpenAiChatModel openAiChatModel;

    @Override
    public String generate(String message) {
        return openAiChatModel.call(message);
    }
}
