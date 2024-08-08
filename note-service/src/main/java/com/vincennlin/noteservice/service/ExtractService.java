package com.vincennlin.noteservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractService {

    String extractTextFromFile(MultipartFile file);

//    String extractTextFromImage(ExtractLanguage language, MultipartFile imageFile);
}
