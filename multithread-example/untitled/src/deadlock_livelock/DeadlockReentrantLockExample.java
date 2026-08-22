package deadlock_livelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadlockReentrantLockExample {

//    private static final ReentrantLock lock1 = new ReentrantLock();
//    private static final ReentrantLock lock2 = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {

        Lock lock1 = new ReentrantLock();
        Lock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(() -> {
            try {
                lock1.lock();
                System.out.println("thread1 - lock1.lock()");

                Thread.sleep(2000);

                try {
                    lock2.lock();
                    System.out.println("thread1 - lock2.lock()");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock2.unlock();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock1.unlock();
            }

        });

        Thread thread2 = new Thread(() -> {
            try {
                lock2.lock();
                System.out.println("thread2 - lock2.lock()");

                Thread.sleep(2000);

                try {
                    lock1.lock();
                    System.out.println("thread2 - lock1.lock()");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    lock1.unlock();
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                lock2.unlock();
            }
        });

        /*Thread thread1 = new Thread(() -> {
            lock1.lock();
            System.out.println("thread1 - lock1.lock()");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                lock2.lock();
                try {
                    System.out.println("thread1 - lock2.lock()");
                } catch (Exception e) {
                    throw new RuntimeException();
                } finally {
                    lock2.unlock();
                }
            } catch (Exception e) {
                throw new RuntimeException();
            } finally {
                lock1.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock2.lock();
            System.out.println("thread2 - lock2.lock()");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                lock1.lock();
                try {
                    System.out.println("thread2 - lock1.lock()");
                } catch (Exception e) {
                    throw new RuntimeException();
                } finally {
                    lock1.unlock();
                }
            } catch (Exception e) {
                throw new RuntimeException();
            } finally {
                lock2.unlock();
            }
        });*/

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("End DeadlockReentrantLockExample");
    }
}
