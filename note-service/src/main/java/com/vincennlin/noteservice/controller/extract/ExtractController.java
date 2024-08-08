package com.vincennlin.noteservice.controller.extract;

import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import com.vincennlin.noteservice.service.ExtractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class ExtractController implements ExtractControllerSwagger{

    private final ExtractService extractService;

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/extract/file")
    public ResponseEntity<String> extractTextFromFile(@RequestPart("file") MultipartFile file) {

        String text = extractService.extractTextFromFile(file);

        return new ResponseEntity<>(text, HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('CREATE')")
//    @PostMapping("/extract/image/{language}")
//    public ResponseEntity<String> extractTextFromImage(@PathVariable(value = "language") ExtractLanguage language,
//                                                       @RequestPart("file") MultipartFile file) {
//
//        String text = extractService.extractTextFromImage(language, file);
//
//        return new ResponseEntity<>(text, HttpStatus.OK);
//    }

    @PreAuthorize("hasAuthority('CREATE')")
    @GetMapping("/fetch/wiki")
    public ResponseEntity<String> fetchTextFromWiki(@RequestParam("keyword") String keyword) {

        String text = extractService.fetchTextFromWiki(keyword);

        return new ResponseEntity<>(text, HttpStatus.OK);
    }
}
