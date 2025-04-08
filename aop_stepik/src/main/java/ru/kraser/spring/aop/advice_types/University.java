package ru.kraser.spring.aop.advice_types;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class University {
    private final List<Student> students = new ArrayList<>();

    public void addStudents() {
        Student student1 = new Student("student1", 4, 7.5);
        Student student2 = new Student("student2", 2, 8.3);
        Student student3 = new Student("student3", 1, 9.1);

        this.students.add(student1);
        this.students.add(student2);
        this.students.add(student3);
    }

    public List<Student> getStudents() {
        System.out.println("Information from method getStudents():");
        System.out.println(students);
        return students;
    }

    public Student getStudent(Integer index) {
        System.out.println("Return Student");
        return this.students.get(index);
    }

    public Student getStudentWithException() {
        System.out.println("Will not return the Student");
        return this.students.get(10);  // Студент с таким индексом - отсутствует !!!
    }

    public List<Student> getStudentsWithException() {
        System.out.println("\nStart method - getStudentsWithException()");
        Student student10 = this.students.get(10);  // Студент с таким индексом - отсутствует !!!
        System.out.println("End method - getStudentsWithException()");
        return students;
    }
}
