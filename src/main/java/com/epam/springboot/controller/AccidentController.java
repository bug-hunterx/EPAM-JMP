package com.epam.springboot.controller;

import com.epam.springboot.modal.Accidents;
import com.epam.springboot.repository.AccidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by bill on 16-5-22.
 */
@RestController
public class AccidentController {
    @Autowired
    private AccidentRepository repository;

    @RequestMapping("/accidents")
    List<Accidents> accidents() {
        return repository.findAll();
    }
}
