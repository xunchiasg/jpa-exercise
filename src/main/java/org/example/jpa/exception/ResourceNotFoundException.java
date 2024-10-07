package org.example.jpa.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(){
        super("Application Resource not found");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

}
