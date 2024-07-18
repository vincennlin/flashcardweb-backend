package com.vincennlin.noteservice.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "flashcard-ws")
public class FlashcardServiceClient {
}
