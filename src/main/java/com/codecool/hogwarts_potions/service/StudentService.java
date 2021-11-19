package com.codecool.hogwarts_potions.service;

import com.codecool.hogwarts_potions.model.Student;
import com.codecool.hogwarts_potions.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void addStudent(Student student) {
        if(student != null) studentRepository.save(student);
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateStudentById(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        if (student != null && updatedStudent != null) {
            student.setName(updatedStudent.getName());
            student.setHouseType(updatedStudent.getHouseType());
            student.setPetType(updatedStudent.getPetType());
        }
    }

    public void deleteStudentById(Long id) {
        studentRepository.findById(id).ifPresent(student -> studentRepository.delete(student));
    }
}
