package com.bostonhacks.backend;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Service
public class OCRService {

    private static final Logger logger = LoggerFactory.getLogger(OCRService.class);
    private static final List<String> SUPPORTED_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg", ".tiff", ".bmp", ".gif");

    private final ITesseract tesseract;

    @Value("${ocr.tessdata.path:tessdata}")
    private String tessdataPath;

    @Value("${ocr.language:eng}")
    private String ocrLanguage;

    public OCRService() {
        tesseract = new Tesseract();
    }

    private void initializeTesseract() {
        if (tessdataPath != null) {
            tesseract.setDatapath(tessdataPath);
        }
        if (ocrLanguage != null) {
            tesseract.setLanguage(ocrLanguage);
        }
        logger.info("OCR Service initialized with tessdata path: {} and language: {}", tessdataPath, ocrLanguage);
    }

    public String extractTextFromImage(File imageFile) throws TesseractException {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file cannot be null");
        }

        if (!imageFile.exists()) {
            throw new IllegalArgumentException("Image file does not exist: " + imageFile.getAbsolutePath());
        }

        if (!imageFile.isFile()) {
            throw new IllegalArgumentException("Path is not a file: " + imageFile.getAbsolutePath());
        }

        if (!isValidImageFormat(imageFile)) {
            throw new IllegalArgumentException("Unsupported image format. Supported formats: " + SUPPORTED_EXTENSIONS);
        }

        try {
            initializeTesseract();
            logger.debug("Processing OCR for file: {}", imageFile.getAbsolutePath());
            String extractedText = tesseract.doOCR(imageFile);
            logger.debug("OCR completed successfully for file: {}", imageFile.getAbsolutePath());
            return extractedText != null ? extractedText.trim() : "";
        } catch (TesseractException e) {
            logger.error("OCR processing failed for file: {}", imageFile.getAbsolutePath(), e);
            throw e;
        }
    }

    private boolean isValidImageFormat(File imageFile) {
        String fileName = imageFile.getName().toLowerCase();
        return SUPPORTED_EXTENSIONS.stream().anyMatch(fileName::endsWith);
    }
}