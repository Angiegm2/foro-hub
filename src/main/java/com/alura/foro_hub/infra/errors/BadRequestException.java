package com.alura.foro_hub.infra.errors;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String messageException){
        super(messageException);
    }
}
