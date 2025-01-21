package com.alura.foro_hub.infra.errors;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String messageException){
        super(messageException);
    }
}
