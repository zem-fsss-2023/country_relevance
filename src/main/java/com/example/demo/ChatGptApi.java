package com.example.demo;
import feign.Headers;
import feign.Param;
import feign.RequestLine;


interface ChatGptApi {
    @RequestLine("GET /v1/models")
    @Headers("Authorization: Bearer {apiKey}")
    GptModelsResponseDto getModels(@Param("apiKey") String apiKey);

    @RequestLine("POST /v1/chat/completions")
    @Headers({"Authorization: Bearer {apiKey}", "Content-Type: application/json"})
    GptAutocompletionResponseDto generateTask(GptAutocompletionRequestDto autocompletion, @Param("apiKey") String apiKey);

}