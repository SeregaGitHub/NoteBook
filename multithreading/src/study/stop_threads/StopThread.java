package study.stop_threads;

public class StopThread {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Main start");

        Example thread = new Example();
        thread.start();
        Thread.sleep(2000);
//        thread.stop();       //  так нельзя - грубое прерывание
        thread.interrupt();    //  прерывание потока, если в нём это прописано
                               //  если поток в это время будет спать (Thread.sleep()) - будет InterruptException !!!
        thread.join();
        System.out.println("Main end");
    }
}

class Example extends Thread {
    double sqrtSum = 0.0;

    @Override
    public void run() {
        for (int i = 0; i <= 1000000000; i++) {
            if (isInterrupted()) {
                System.out.println("Thread must to be stop");
                System.out.println("Doing all things what we need");
                // And when
                return;
            }
            sqrtSum += Math.sqrt(i);
        }
        System.out.println(sqrtSum);
    }
}
