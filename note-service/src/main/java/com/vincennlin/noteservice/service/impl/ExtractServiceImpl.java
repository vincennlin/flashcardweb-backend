package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import com.vincennlin.noteservice.service.ExtractService;
import lombok.AllArgsConstructor;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ExtractServiceImpl implements ExtractService {

    private final Environment env;

    @Override
    public String extractTextFromFile(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        if (fileName == null) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from file: file name is null");
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

        return switch (fileExtension) {
            case "pdf" -> extractTextFromPdf(file);
            case "txt" -> extractTextFromTxt(file);
            case "docx" -> extractTextFromDocx(file);
            default ->
                    throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from file: unsupported file type");
        };
    }

    private String extractTextFromPdf(MultipartFile pdfFile) {

        try (PDDocument document = PDDocument.load(pdfFile.getInputStream())) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from .pdf file");
        }
    }

    private String extractTextFromTxt(MultipartFile txtFile) {

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(txtFile.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from .txt file");
        }
    }

    private String extractTextFromDocx(MultipartFile docxFile) {

        try (XWPFDocument document = new XWPFDocument(docxFile.getInputStream())) {
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            return extractor.getText();
        } catch (IOException e) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from .docx file");
        }
    }

    private String extractTextFromImage(ExtractLanguage language, MultipartFile imageFile) {

        String tesseractTessDataPath = env.getProperty("tesseract.tessdata.path");
        String tesseractLanguage = language.getTesseractLanguage();

        try {
            File tempFile = File.createTempFile("upload-", ".png");
            imageFile.transferTo(tempFile);

            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath(tesseractTessDataPath);
            tesseract.setLanguage(tesseractLanguage);

            String text = tesseract.doOCR(tempFile);

            Files.delete(tempFile.toPath());

            return text;
        } catch (IOException | TesseractException e) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Failed to extract text from image file: " + e.getMessage());
        }
    }
}
