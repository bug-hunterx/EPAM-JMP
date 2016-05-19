package com.epam.mentoring.restapi.web.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by pengfrancis on 16/5/18.
 */
public class UnauthorizedError extends HttpException {

    public UnauthorizedError() {
        super(HttpStatus.UNAUTHORIZED);
    }
    public UnauthorizedError(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
