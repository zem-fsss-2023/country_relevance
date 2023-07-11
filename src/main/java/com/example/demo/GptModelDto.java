package com.example.demo;

public record GptModelDto(
        String id,
        String object,
        Long created,
        String owned_by
) {}
