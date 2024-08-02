package com.example.ecommercemini.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){super("Resource Not Found on Server");}
    public ResourceNotFoundException(String message){super(message);}
}
