package com.epam.mentoring.restapi.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by pengfrancis on 16/5/18.
 */
public abstract class HttpException extends RuntimeException{
    private HttpStatus httpStatus;
    private String message;

    public HttpException(HttpStatus httpStatus){
        this.httpStatus= httpStatus;
        //if(httpStatus!=null)

    }
    public HttpException(HttpStatus httpStatus, String message){
        this.httpStatus= httpStatus;
        this.message= message;

    }
    public HttpStatus getStatusCode(){
        return httpStatus;
    }
    public String getMessage(){
        return message;
    }
}
