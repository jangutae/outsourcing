package com.example.outsourcing.common.constants;

public enum AccountRole {
    USER("USER"),
    BOSS("BOSS");

    private final String message;

    AccountRole(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
