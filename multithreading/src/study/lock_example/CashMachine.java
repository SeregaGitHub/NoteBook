package study.lock_example;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CashMachine {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Employee employee2 = new Employee("Alex", lock);
        Employee employee3 = new Employee("Sasha", lock);
        Employee employee4 = new Employee("Oleg", lock);
        Thread.sleep(1000);
        Employee2 employee5 = new Employee2("Tim", lock);

/*
        Oleg is waiting...
        Alex is waiting...
        Oleg using cash machine...
        Sasha is waiting...
        Tim do not want to wait. Tim - go away !!!
        Oleg end using cash machine.
        Alex using cash machine...
        Alex end using cash machine.
        Sasha using cash machine...
        Sasha end using cash machine.
*/
    }
}

class Employee extends Thread {
    String name;
    Lock lock;

    public Employee(String name, Lock lock) {
        this.name = name;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {
        System.out.println(name + " is waiting...");
        lock.lock();
        System.out.println(name + " using cash machine...");
        try {
            Thread.sleep(2000);
            System.out.println(name + " end using cash machine.");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}

class Employee2 extends Thread {
    String name;
    Lock lock;

    public Employee2(String name, Lock lock) {
        this.name = name;
        this.lock = lock;
        this.start();
    }

    @Override
    public void run() {    // lock.tryLock() - если попытка неудачная, то больше не будет пытаться
        if (lock.tryLock()) {
            try {
                System.out.println(name + " using cash machine...");
                Thread.sleep(2000);
                System.out.println(name + " end using cash machine.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println(name + " do not want to wait. " + name + " - go away !!!");
        }
    }
}