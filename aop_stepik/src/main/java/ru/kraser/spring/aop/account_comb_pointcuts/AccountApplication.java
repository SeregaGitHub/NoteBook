package ru.kraser.spring.aop.account_comb_pointcuts;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.kraser.spring.aop.Config;

public class AccountApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Config.class);

        Account account = context.getBean("accountBean", Account.class);

        account.getAccount();
        account.deletePost();
        account.addAccount(); // Нет аспектов
        account.getPost();

        context.close();
    }
}
