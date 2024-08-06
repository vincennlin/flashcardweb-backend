package com.vincennlin.noteservice.controller.extract;

import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.service.ExtractService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class ExtractController {

    private final ExtractService extractService;

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/extract/pdf")
    public ResponseEntity<String> extractTextFromPdf(@RequestPart("file") MultipartFile file) {

        String text = extractService.extractTextFromPdf(file);

        return new ResponseEntity<>(text, HttpStatus.OK);
    }
}
