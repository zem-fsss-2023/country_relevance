package com.example.demo;

import java.util.List;

public class GptAutocompletionRequestDto
{

    public GptAutocompletionRequestDto(String model, String content) {
        GptAutocompletionMessageDto message = new GptAutocompletionMessageDto("user", content);
        this.model = model;
        this.messages = List.of(message);
    }

    String model;
    List<GptAutocompletionMessageDto> messages;
}
