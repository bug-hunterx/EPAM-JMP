package com.epam.mentoring.restapi.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by pengfrancis on 16/5/18.
 */
public class BadRequestError extends HttpException {

    public BadRequestError() {
        super(HttpStatus.BAD_REQUEST);
    }
    public BadRequestError(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
