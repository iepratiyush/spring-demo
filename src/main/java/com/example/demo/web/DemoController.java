package com.example.demo.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.annotations.LogRequestResponse;

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
    
}
