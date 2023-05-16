package org.example;

import org.example.domain.Student;
import org.example.repository.StudentMysqlRepository;
import org.example.repository.StudentRepository;

import java.sql.*;

public class Main {


    public static void main(String[] args) {
        StudentRepository studentRepository = new StudentMysqlRepository();

        System.out.println(studentRepository.findAll());

        Student student = studentRepository.findById(1);

        System.out.println(student);

        Student s1 = Student.builder()
                .surname("Petrikov")
                .age(20)
                .groupID("EO-22-1")
                .build();

        StudentRepository studentRepository1 = new StudentMysqlRepository();
        studentRepository1.save(s1);

        System.out.println(studentRepository1.findAll());

    }

}