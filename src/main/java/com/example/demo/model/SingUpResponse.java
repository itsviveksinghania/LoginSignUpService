package com.example.demo.model;

import lombok.Data;

@Data
public class SingUpResponse {

    private String message;
    public SingUpResponse(String message){
        this.message = message;
    }
}
