package com.example.outsourcing.common.constants;

public enum AccountStatus {
    USE("Y"),
    NOT_USE("N");

    private final String message;


    AccountStatus(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
