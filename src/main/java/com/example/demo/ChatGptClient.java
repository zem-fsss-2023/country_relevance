package com.example.demo;


public interface ChatGptClient {
    GptModelsResponseDto getModels();

    String generateNoteText();
}