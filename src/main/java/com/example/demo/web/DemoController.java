package com.example.demo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.demo.annotations.LogRequestResponse;
import com.example.demo.models.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Pratiyush Prakash
 * 
 * All endpoints resides here
 */
@RestController
@RequestMapping("/api/v1")
public class DemoController {

    /**
     * This is a sample secured API call
     * @return String
     */
    @LogRequestResponse
    @GetMapping(value = "/secured/hello-world")
    public String securedCall() {
        return "hello world secured!!";
    }

    /**
     * This is a sample un-secured API call
     * @return String
     */
    @LogRequestResponse
    @GetMapping(value = "/hello-world")
    public String unsecuredCall() {
        return "hello world!!";
    }

    /**
     * This is a sample GET call to return object
     * @return Map of name and age
     */
    @LogRequestResponse
    @GetMapping(value = "/demo-object")
    public Map<String, Integer> demoObjectMap() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Pratiyush", 29);
        return map;
    }

    /**
     * This is a sample GET call to test different response status
     * @return ResponseEntity
     */
    @LogRequestResponse
    @GetMapping(value = "/demo-response-entity")
    public ResponseEntity<String> demoResponseEntity(@RequestParam Integer code) {
        switch (code) {
            case 200:
                return new ResponseEntity<String>("Hello world!!", HttpStatus.ACCEPTED);
            case 400:
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This endpoint will return data as a stream
     * @return ResponseEntity<StreamingResponseBody>
     */
    @GetMapping("/stream/employee/all")
    public ResponseEntity<StreamingResponseBody> streamEmployees(
    ) {
        List<Employee> employees = new ArrayList<>();

        // Emulate having 10k rows
        // For 10k you won't notice significant improvement
        // Increase this number and you will see how performance gets impacted
        for (int i = 1; i <= 10000; i++) {
            Employee employee = Employee.builder().id(i).name("name " + i).departmentId((int)(Math.random() * 10000)).build();
            employees.add(employee);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        StreamingResponseBody responseBody = outputStream -> {
            
            for (Employee employee: employees) {
                String jsonChunk = objectMapper.writeValueAsString(employee);
                outputStream.write(jsonChunk.getBytes());
                // We have to add a token so that we can split it using this in client side
                outputStream.write("#".getBytes());
            }
        };

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "application/json").body(responseBody);
    }

    /**
     * This endpoint will return data in one go
     * @return List<Employee>
     */
    @GetMapping("/normal/employee/all")
    public List<Employee> streamEmployeesNormal() {
        List<Employee> employees = new ArrayList<>();

        // Emulate having 10k rows
        // For 10k you won't notice significant improvement
        // Increase this number and you will see how performance gets impacted
        for (int i = 0; i < 10000; i++) {
            Employee employee = Employee.builder().id(i).name("name " + i).departmentId((int)(Math.random() * 10000)).build();
            employees.add(employee);
        }

        return employees;
    }
    
    
}
