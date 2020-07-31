package com.letoan.exception;

import com.letoan.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CreatedAdvice {
    @ResponseBody
    @ExceptionHandler(CreatedException.class)
    @ResponseStatus(HttpStatus.CREATED)
    ResponseMessage createdHandler(CreatedException ex){
        return new ResponseMessage(ex.getMessage());
    }
}
