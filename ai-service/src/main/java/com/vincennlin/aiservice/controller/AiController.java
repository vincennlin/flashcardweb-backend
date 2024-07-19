package com.vincennlin.aiservice.controller;

import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class AiController {

    private AiService aiService;

    @GetMapping("/ai/generate")
    public ResponseEntity<String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        String generation = aiService.generate(message);

        return new ResponseEntity<>(generation, HttpStatus.OK);
    }
}
