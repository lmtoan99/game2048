package com.letoan.exception;

public class CreatedException extends RuntimeException{
    public CreatedException(String username){
        super("Created username " + username);
    }
}
