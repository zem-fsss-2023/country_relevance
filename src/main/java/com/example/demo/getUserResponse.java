package com.example.demo;

import java.util.List;

public record getUserResponse(Integer page, List<User> data) {
}
