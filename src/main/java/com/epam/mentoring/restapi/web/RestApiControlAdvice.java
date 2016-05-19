package com.epam.mentoring.restapi.web;

import com.epam.mentoring.restapi.web.exception.BadRequestError;
import com.epam.mentoring.restapi.web.exception.InternalServerError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by pengfrancis on 16/5/15.
 */

@ControllerAdvice
public class RestApiControlAdvice {

    @ExceptionHandler(value=RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(RuntimeException e) {
        return "INTERNAL_SERVER_ERROR";
    }

    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(Exception e) {
        //log it
        return "INTERNAL_SERVER_ERROR";

    }

    @ExceptionHandler(value=InternalServerError.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handle(InternalServerError e) {
        return e.getMessage()== null ? "INTERNAL_SERVER_ERROR" : "INTERNAL_SERVER_ERROR: "+ e.getMessage();
    }


    @ExceptionHandler(value=BadRequestError.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(BadRequestError e) {
        return e.getMessage()== null ? "BAD_REQUEST" : "BAD_REQUEST: "+ e.getMessage();
    }


}
