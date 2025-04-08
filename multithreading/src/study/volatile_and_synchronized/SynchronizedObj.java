package study.volatile_and_synchronized;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedObj {

    void mobileCall() {
        synchronized (lock) {
            System.out.println("Mobile call start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Mobile call end");
        }
    }

    void skypeCall() {
        synchronized (lock) {
            System.out.println("Skype call start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Skype call end");
        }
    }

    void whatsappCall() {
        synchronized (lock) {
            System.out.println("Whatsapp call start");
            try {
                Thread.sleep(7000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Whatsapp call end");
        }
    }

    // 3 метода синхронизируются с помощью статического объекта (очерёдность может быть нарушена)
    static final Object lock = new Object();

    public static void main(String[] args) {
        Thread threadMobile = new Thread(new RunnableImplMobile());
        Thread threadSkype = new Thread(new RunnableImplSkype());
        Thread threadWhatsapp = new Thread(new RunnableImplWhatsapp());

        threadMobile.start();
        threadSkype.start();
        threadWhatsapp.start();

    }
}

class RunnableImplMobile implements Runnable {
    @Override
    public void run() {
        new SynchronizedObj().mobileCall();
    }
}

class RunnableImplSkype implements Runnable {
    @Override
    public void run() {
        new SynchronizedObj().skypeCall();
    }
}

class RunnableImplWhatsapp implements Runnable {
    @Override
    public void run() {
        new SynchronizedObj().whatsappCall();
    }
}