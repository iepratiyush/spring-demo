package com.example.demo.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Pratiyush
 * 
 * Employee class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private Integer id;
    private Integer departmentId;
}
