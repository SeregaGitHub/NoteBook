package multithreading;

public class RobotSteps {
    public static void main(String[] args) throws InterruptedException {

        var thread1 = new Thread(new Foot(1));
        var thread2 = new Thread(new Foot(2));

        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        thread1.interrupt();
        thread2.interrupt();
    }

    private static class Foot implements Runnable {

        private final int number;

        public Foot(int number) {
            this.number = number;
        }

        public void makeStep() {
            System.out.println("Make step by leg: " + number);
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                makeStep();
            }
        }
    }
}
