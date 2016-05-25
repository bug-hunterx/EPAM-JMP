package com.epam.mentoring.restapi.controller;
 
 import java.util.List;
 
 import javax.xml.ws.http.HTTPException;
 
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.web.bind.annotation.PathVariable;
 import org.springframework.web.bind.annotation.RequestBody;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RequestMethod;
 import org.springframework.web.bind.annotation.ResponseBody;
 import org.springframework.web.bind.annotation.RestController;
 
 import com.epam.mentoring.restapi.modal.Employee;
 import com.epam.mentoring.restapi.repository.EmployeeRepository;
 
 /**
  * Created by pengfrancis on 16/5/19.
  */
 @RestController
 public class EmployeeController {
 
 	@Autowired
 	private EmployeeRepository employeeRepository;
 
 	// todo add Restful services to getAll
 	@RequestMapping(method = RequestMethod.GET, value = "/employees")
 	public @ResponseBody List<Employee> getAll() {
 		return employeeRepository.findAll();
 	}
 
 	// todo add Restful services to get
 	@RequestMapping(method = RequestMethod.GET, value = "/employees/{id}")
 	public @ResponseBody Employee get(@PathVariable Long id) {
 		return employeeRepository.findOne(id);
 	}
 
 	// todo add Restful services to getByName
 	@RequestMapping(method = RequestMethod.GET, value = "/employees/name/{name}")
 	public @ResponseBody List<Employee> getByName(@PathVariable String name) {
 		return employeeRepository.findByName(name);
 	}
 
 	// todo add Restful services to create,
 	@RequestMapping(method = RequestMethod.POST, value = "/employees", headers="Accept=application/json")
 	public @ResponseBody long save(@RequestBody Employee employee) {
 		employeeRepository.save(employee);
 		return employee.getId();
 	}
 
 	// todo add Restful services to put, response 404(Not Found) when encounter
 	// the exception that the id is not exist
 	@RequestMapping(method = RequestMethod.PUT, value = "/employees", headers="Accept=application/json")
 	public @ResponseBody void update(@RequestBody Employee employee) {
 		if (employeeRepository.findOne(employee.getId()) == null) {
 			throw new HTTPException(404);
 		}
 		employeeRepository.save(employee);
 	}
 
 	// todo add Restful services to delete
 	@RequestMapping(method = RequestMethod.POST, value = "/employees/{id}", headers="Accept=application/json")
 	public @ResponseBody void delete(@PathVariable Long id) {
 		employeeRepository.delete(id);
 	}
 }