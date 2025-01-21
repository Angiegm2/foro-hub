package com.alura.foro_hub.infra.errors;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String messageException){
        super(messageException);
    }
}
