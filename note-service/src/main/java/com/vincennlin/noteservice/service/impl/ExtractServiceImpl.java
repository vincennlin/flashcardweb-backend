package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.service.ExtractService;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@AllArgsConstructor
@Service
public class ExtractServiceImpl implements ExtractService {

    @Override
    public String extractTextFromPdf(MultipartFile pdfFile) {

        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from PDF file");
        }
    }
}
