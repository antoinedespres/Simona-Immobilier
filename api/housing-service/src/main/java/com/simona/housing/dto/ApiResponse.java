package com.simona.housing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ApiResponse<T> implements Serializable {
    private T data;
    private String message;

    // Allow Jackson to create a JSON response using this class
    public ApiResponse() {
    }

    public ApiResponse(T data, String message) {
        this.message = message  ;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
