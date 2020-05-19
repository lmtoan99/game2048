package com.letoan.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(){
        super("Not found");
    }
}
