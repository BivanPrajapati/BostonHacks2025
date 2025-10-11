package com.bostonhacks.backend;

import com.google.genai.Client;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import java.util.Map;
import java.util.HashMap;

import static com.bostonhacks.backend.Gemini.client;

@RestController
public class RequestController {

  @GetMapping("/text")
  public String getText(String input) {
    // todo
    return input;
  }

  @PostMapping("/upload-text")
  public ResponseEntity<Map<String, Object>> uploadText(@RequestParam("file") MultipartFile file) {
      Map<String, Object> response = new HashMap<>();

      if (file.isEmpty()) {
          response.put("success", false);
          response.put("message", "Please select a file to upload.");
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }

      String originalFilename = file.getOriginalFilename();
      if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".txt")) {
          response.put("success", false);
          response.put("message", "Only .txt files are allowed.");
          return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }
      response.put("success", true);
      response.put("message", "Uploaded file successfully!");
      return new ResponseEntity<>(response, HttpStatus.OK);

  }

      @PostMapping("/upload-image")
  public String uploadImage(
      @RequestParam("file") MultipartFile file,
      RedirectAttributes redirectAttributes
  ) {
     return "";
  }

  @GetMapping("/image-advice")
  public String getImageAdvice(@RequestParam("file") String filename) {
    client.models.generateContent(
        "gemini-2.5-flash",
        "Please search this image and examine if there's any personally identifiable information.",
        null);
    return "";
  }
}
