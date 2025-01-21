package com.alura.foro_hub.infra.errors;

public class ValidateException extends RuntimeException{
    public ValidateException(String messageException){
        super(messageException);
    }
}
