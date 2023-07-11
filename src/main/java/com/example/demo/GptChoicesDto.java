package com.example.demo;

public record GptChoicesDto(GptAutocompletionMessageDto message, String finish_reason, Integer index) {

}
