package com.vincennlin.flashcardservice.client;

import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerRequest;
import com.vincennlin.flashcardservice.payload.flashcard.evaluate.EvaluateShortAnswerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/evaluate/short-answer")
    ResponseEntity<EvaluateShortAnswerResponse> evaluateShortAnswer(@RequestBody EvaluateShortAnswerRequest request,
                                                                    @RequestHeader("Authorization") String authorization);

}
