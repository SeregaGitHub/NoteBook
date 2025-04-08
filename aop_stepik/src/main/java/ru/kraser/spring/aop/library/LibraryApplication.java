package ru.kraser.spring.aop.library;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kraser.spring.aop.Config;

public class LibraryApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        UniLibrary uniLibrary = context.getBean("uniLibraryBean", UniLibrary.class);
        SchoolLibrary schoolLibrary = context.getBean("schoolLibraryBean", SchoolLibrary.class);

        uniLibrary.getBook();
        uniLibrary.getMagazine();
        schoolLibrary.getBook();

        uniLibrary.returnBook();
        schoolLibrary.returnBook();

        System.out.println(schoolLibrary.returnTypeIntBook());
        System.out.println(schoolLibrary.returnTypeStringBook());


        context.close();
    }
}
