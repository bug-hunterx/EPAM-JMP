package com.epam.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Alexey on 22.05.2016.
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
