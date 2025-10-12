package com.bostonhacks.backend;

import com.google.genai.Client;

public final class Gemini {
    private static Client client;
    private static Gemini INSTANCE;

    private Gemini() {
        client = new Client();
    }

    public static synchronized Gemini getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Gemini();
        }
        return INSTANCE;
    }

    public Client getGemini() {
        return client;
    }
}
