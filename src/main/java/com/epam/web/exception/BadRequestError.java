package com.epam.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by Alexey on 22.05.2016.
 */
public class BadRequestError extends HttpException {

    public BadRequestError() {
        super(HttpStatus.BAD_REQUEST);
    }
    public BadRequestError(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
