package com.epam.mentoring.restapi.controller;

import com.epam.mentoring.restapi.modal.Department;
import com.epam.mentoring.restapi.repository.DepartmentRepository;
import com.epam.mentoring.restapi.web.exception.BadRequestError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Francis
 * Date: 12-5-13
 * Time: 上午8:42
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping(value = "/api/")
public class DepartmentController {
    private static org.apache.log4j.Logger LOG= org.apache.log4j.Logger.getLogger(DepartmentController.class);
    @Autowired
    private DepartmentRepository repository;

    @RequestMapping(method= RequestMethod.GET, value= "/departments")
    public @ResponseBody List<Department> getDepartments(){
        List<Department> list= repository.findAll();

        return list;
    }

    ////produces = {"application/json","application/xml"})
    @RequestMapping(method= RequestMethod.GET, value= "/departments/{id}", headers="Accept=application/json")
    public @ResponseBody
    Department getDepartment(@PathVariable long id){
        Department department= repository.findOne(id);

        return department;
    }

    @RequestMapping(method= RequestMethod.POST, value= "/departments", headers="Accept=application/json")
    public @ResponseBody Long save(@RequestBody Department s){
        LOG.info("creating Department: "+ s.getName());
            //validate(s, false);
        repository.save(s);
        return s.getId();
    }

    @RequestMapping(method= RequestMethod.PUT, value= "/departments/{id}", headers="Accept=application/json")
public @ResponseBody void update(@RequestBody Department s, @PathVariable long id){
        LOG.info("update Department: "+ id+ ","+ s.getName());
        if(s.getId()!= id)
            throw new BadRequestError("id is not match");
        repository.save(s);
    }

    @RequestMapping(method= RequestMethod.DELETE, value= "/departments/{id}", headers="Accept=application/json")
    public @ResponseBody void delete(@PathVariable long id){
        LOG.info("delUser: "+ id);
        repository.delete(id);
    }


}
