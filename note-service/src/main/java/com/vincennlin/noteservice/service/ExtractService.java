package com.vincennlin.noteservice.service;

import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import org.springframework.web.multipart.MultipartFile;

public interface ExtractService {

    String extractTextFromPdf(MultipartFile pdfFile);

    String extractTextFromImage(ExtractLanguage language, MultipartFile imageFile);
}
