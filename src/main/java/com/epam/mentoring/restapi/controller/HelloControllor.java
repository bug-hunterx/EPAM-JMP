package com.epam.mentoring.restapi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by pengfrancis on 16/5/19.
 */
@RestController
public class HelloControllor {

    @RequestMapping(value = "hello/{name}")
    public String hello(@PathVariable String name){
        return "hello, "+ name;
    }
}
