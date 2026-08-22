package synchronized_package;

public class SynchronizedExample {

    private static Integer count = 0;

    private static final Object lock = new Object();  // can be used for lock code

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCountSynLock();    // Very good !!!
                //incrementCountSynMeth();  // OK !!!
                // incrementCountNotSyn();  // Wrong answer !!!
                // incrementCountSynCl();   // Slow solution
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                incrementCountSynLock();
                //incrementCountSynMeth();
                // incrementCountNotSyn();
                // incrementCountSynCl();
            }
        });

        t1.start();
        t2.start();
    }

    private static void incrementCountSynLock() {
        synchronized (lock) {
            count++;
        }
    }

    private static synchronized void incrementCountSynMeth() {
        count++;
    }

    private static void incrementCountNotSyn() {
        count++;
    }

    private static void incrementCountSynCl() {
        synchronized (SynchronizedExample.class) {
            count++;
        }
    }
}

