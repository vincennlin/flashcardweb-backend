package com.vincennlin.courseservice.client;

import com.vincennlin.courseservice.payload.request.FlashcardIdsRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "flashcard-ws")
public interface FlashcardServiceClient {

    @PostMapping("/api/v1/flashcards/ids")
    ResponseEntity<List<Long>> getFlashcardIdsByCurrentUserIdAndIds(@RequestBody FlashcardIdsRequest request,
                                                                    @RequestHeader("Authorization") String authorization);
}
