package com.example.demo;

import co.elastic.clients.elasticsearch.xpack.usage.Counter;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class ChatGptClientImpl implements ChatGptClient {

    private final String apiKey;

    private final ChatGptApi chatGptApi;

    @Autowired
    public ChatGptClientImpl(@Value("${gpt.api.url}") final String url,
                             @Value("${gpt.api.key}") final String apiKey) {
        this.apiKey = apiKey;
        this.chatGptApi = Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .target(ChatGptApi.class, url);
    }

    @Override
    public GptModelsResponseDto getModels() {
        return chatGptApi.getModels(apiKey);
    }

    @Override
    public String generateText(String text) {
        GptAutocompletionRequestDto requestBody = new GptAutocompletionRequestDto(
                "gpt-3.5-turbo",
                text
        );
        GptAutocompletionResponseDto response = chatGptApi.generateTask(requestBody, apiKey);

        return response.getAutocompletedMesage();
    }
}