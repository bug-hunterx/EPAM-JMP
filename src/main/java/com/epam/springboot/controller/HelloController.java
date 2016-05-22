package com.epam.springboot.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bill on 16-5-22.
 */
@RestController
public class HelloController {

    @RequestMapping(value = "hello/{name}")
    public String hello(@PathVariable String name){
        return "hello, "+ name;
    }
}
