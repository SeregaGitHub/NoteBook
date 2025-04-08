package ru.kraser.spring.aop.advice_types;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class UniversityAspect {

    @Before("execution(* getStudents())")
    public void beforeGetStudents() {
        System.out.println("From Aspect: beforeGetStudents()");
    }

    @AfterReturning(pointcut =  "execution(* getStudents())",
                    returning = "students")
    public void afterGetStudents(List<Student> students) {  // названия returning и List<Student> - должны совпадать !!!
                                                            // с помощью @AfterReturning изменяем регистр имён студентов
        for (Student s: students) {
            s.setName(s.getName().toUpperCase());
        }

        System.out.println("\nFrom Aspect: afterGetStudents()");
    }

    @Around("execution(public Student getStudent(Integer))")
    public Object aroundGetStudentAdvice(ProceedingJoinPoint joinPoint) throws Throwable {  // return type - должен совпадать либо быть Object !!!
        System.out.println("From UniversityAspect: start aroundGetStudentAdvice()...");

        // Теперь с объектом возможна любая работа
        Object object = joinPoint.proceed();

        System.out.println("From UniversityAspect: end aroundGetStudentAdvice()...");
        return object;
    }

    @Around("execution(public Student getStudentWithException())")
    public Object aroundGetStudentWithExceptionAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("From UniversityAspect: start aroundGetStudentAdvice()...");

        // @Around, в отличие от @AfterThrowing, может обрабатывать Exception
        Object object = null;
        try {
            object = joinPoint.proceed();
        } catch (Throwable e) {
            System.out.println("EXCEPTION - " + e.getMessage());

            // throw e; // Лучше пробросить Exception в main, предварительно проделав с ним необходимую работу

            // Чтобы не возвращать null !!! (Обрабатывать Exception в @Around - не лучшая идея)
            object = new Student("Default name", 0, 0.0);
        }

        System.out.println("From UniversityAspect: end aroundGetStudentAdvice()...");
        return object;
    }

 // @AfterThrowing - не ловит исключение - оно попадёт в метод main !!!
    @AfterThrowing("execution(* getStudentsWithException())")
    public void afterGetStudentsWithException() {
        System.out.println("This is @AfterThrowing. I am - WORK !!! Then exception was thrown:");
    }

/*                              Можно работать с Exception

    @AfterThrowing(pointcut = "execution(* getStudentsWithException())",
                   throwing = "exception")
    public void afterGetStudentsWithException(Throwable exception) {
        System.out.println("This is @AfterThrowing. I am - WORK !!! Then exception was thrown:");
        System.out.println(exception.getCause());
    }*/
}
