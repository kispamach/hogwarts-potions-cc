package com.codecool.hogwarts_potions.controller;

import com.codecool.hogwarts_potions.model.Room;
import com.codecool.hogwarts_potions.model.Student;
import com.codecool.hogwarts_potions.service.RoomService;
import com.codecool.hogwarts_potions.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @PostMapping
    public void addStudent(@RequestBody Student student){
        studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable("id") Long id){
        return studentService.getStudentById(id);
    }

    @PutMapping("/{id}")
    public void updateStudentById(@PathVariable("id") Long id, @RequestBody Student updatedStudent){
        studentService.updateStudentById(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudentById(id);
    }
}
