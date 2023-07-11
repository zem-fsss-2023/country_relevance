package com.example.demo;

import co.elastic.clients.elasticsearch.xpack.usage.Counter;
import com.outbrain.summerschool.domain.gpt.dto.GptAutocompletionRequestDto;
import com.outbrain.summerschool.domain.gpt.dto.GptAutocompletionResponseDto;
import com.outbrain.summerschool.domain.gpt.dto.GptModelsResponseDto;
import com.outbrain.summerschool.domain.note.NoteService;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class ChatGptClientImpl implements ChatGptClient {

    private final String apiKey;
    private final Logger logger = LoggerFactory.getLogger(NoteService.class.getName());
    private final Counter noteGeneratorCounter;
    private final Timer noteGenerationTimer;
    private final ChatGptApi chatGptApi;

    @Autowired
    public ChatGptClientImpl(@Value("${gpt.api.url}") final String url,
                             @Value("${gpt.api.key}") final String apiKey,
                             final MeterRegistry meterRegistry) {
        this.noteGeneratorCounter = meterRegistry.counter("noteGeneratorCounter");
        this.noteGenerationTimer = meterRegistry.timer("noteGeneratorTimer");
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
    public String generateNoteText() {
        GptAutocompletionRequestDto requestBody = new GptAutocompletionRequestDto(
                "gpt-3.5-turbo",
                "create a random to do item that would appear on a random to do list. Return only the description, nothing else"
        );
        logger.info("Generating new Note text");
        noteGeneratorCounter.increment();
        GptAutocompletionResponseDto response = noteGenerationTimer.record(() -> chatGptApi.generateTask(requestBody, apiKey));

        return response.getAutocompletedMesage();
    }
}