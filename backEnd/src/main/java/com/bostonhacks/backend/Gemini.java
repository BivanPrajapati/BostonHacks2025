package com.bostonhacks.backend;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.genai.Client;

/**
 * Automatically reads the API key from environment variables:
 * - GOOGLE_API_KEY
 * - GEMINI_API_KEY
 *
 * GOOGLE_API_KEY takes precedence.
 *
 * This service is lazily initialized to prevent
 * startup failures when the API key is not present.
 */
@Service
@Lazy
public class Gemini {
    private final Client client;

    public Gemini() {
        // The Client constructor automatically picks up the API key from envvars
        this.client = new Client();
    }

    public Client getGemini() {
        return client;
    }
}
