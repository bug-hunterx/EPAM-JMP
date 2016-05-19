package com.epam.mentoring.restapi.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by pengfrancis on 16/5/18.
 */
public class InternalServerError  extends HttpException {

    public InternalServerError() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public InternalServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}

