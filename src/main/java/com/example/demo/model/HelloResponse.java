package com.example.demo.model;

import lombok.Data;

@Data
public class HelloResponse {
    private String message;

    public HelloResponse(String message) {
        this.message = message;
    }
}
