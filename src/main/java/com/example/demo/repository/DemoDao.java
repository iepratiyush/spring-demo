package com.example.demo.repository;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Student;

/**
 * @author Pratiyush
 * 
 *         Repository class
 */
@Repository
public interface DemoDao {

    /**
     * 
     * @param id - student id
     * @return Student - student object
     */
    public Student getStudent(@Param("id") Integer id);
}
