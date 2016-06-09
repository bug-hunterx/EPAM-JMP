package com.epam.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.web.bind.annotation.*;

/**
 * Created by bill on 16-6-5.
 */
@ControllerAdvice(annotations = RestController.class)
public class RestControllerAdvice {
    @ExceptionHandler(ObjectRetrievalFailureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handle(ObjectRetrievalFailureException e) {
        return e.getMessage() + "\n";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(IllegalArgumentException e) {
        return e.getMessage() + "\n";
    }

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
