package com.letoan.exception;

import com.letoan.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseMessage notFoundHandler(NotFoundException ex){
        return new ResponseMessage(ex.getMessage());
    }
}
