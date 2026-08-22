package thread_order;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionOrder {
    public static void main(String[] args) throws InterruptedException {

        MyCondition myCondition = new MyCondition();

        Thread t1 = new Thread(myCondition::printSecond);
        Thread t2 = new Thread(myCondition::printFirst);
        Thread t3 = new Thread(myCondition::printThird);

        t1.setName("T1");
        t2.setName("T2");
        t3.setName("T3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("End of ConditionOrder");
    }
}

class MyCondition {

    private final ReentrantLock lock = new ReentrantLock();

    // Condition help to set order of threads !!!
    private final Condition conditionFirst = lock.newCondition();
    private final Condition conditionSecond = lock.newCondition();

    public void printFirst() {
        lock.lock();
        try {
            System.out.println("printFirst");
            conditionFirst.signal();  // signal about end of printFirst() method
        } finally {
            lock.unlock();
        }
    }

    public void printSecond() {
        lock.lock();
        try {
            conditionFirst.await();    // wait a signal from conditionFirst
            System.out.println("printSecond");
            conditionSecond.signal();  // signal about end of printSecond() method
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void printThird() {
        lock.lock();
        try {
            conditionSecond.await();  // wait a signal from conditionSecond
            System.out.println("printThird");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
