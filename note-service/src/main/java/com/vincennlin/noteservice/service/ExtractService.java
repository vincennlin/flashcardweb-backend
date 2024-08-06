package com.vincennlin.noteservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExtractService {

    String extractTextFromPdf(MultipartFile pdfFile);
}
