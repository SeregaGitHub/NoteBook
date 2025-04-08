package ru.kraser.spring.aop.account_comb_pointcuts;

import org.springframework.stereotype.Component;

@Component("accountBean")
public class Account {

    public void getAccount() {
        System.out.println("We getting some account.");
    }

    public void getPost() {
        System.out.println("We getting some post.");
    }

    public void deleteAccount() {
        System.out.println("We deleted the account.");
    }

    public void deletePost() {
        System.out.println("We deleted the post.");
    }

    public void addAccount() {
        System.out.println("We adding the account.");
    }

    public void addPost() {
        System.out.println("We adding the post.");
    }
}
