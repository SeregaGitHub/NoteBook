package ru.kraser.spring.aop.advice_types;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kraser.spring.aop.Config;

import java.util.List;

public class Study {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        University university = context.getBean("university", University.class);
        university.addStudents();
        List<Student> students = university.getStudents(); //   @AfterReturning может перехватить return метода
                                                           // и записать в students новые изменённые значения
        System.out.println("New values of students, with help of @AfterReturning:");
        System.out.println(students);

        System.out.println("\n========== @Around ==========");

        Student student = university.getStudent(0);
        System.out.println("Student - " + student);

        System.out.println("========== Exception ==========");

        Student studentExc = university.getStudentWithException();
        System.out.println(studentExc);

        System.out.println("========== @Around ==========");

        university.getStudentsWithException();

        context.close();

    }
}
