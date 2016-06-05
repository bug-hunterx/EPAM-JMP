package com.epam.springboot.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bill on 16-6-5.
 */
public class RestControllerAdvice {
    @ExceptionHandler(value=RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(RuntimeException e) {
        return "INTERNAL_SERVER_ERROR_RUNTIME";
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(Exception e) {
        //log it
        return "INTERNAL_SERVER_ERROR_ALL_EXCEPTION";

    }


}
