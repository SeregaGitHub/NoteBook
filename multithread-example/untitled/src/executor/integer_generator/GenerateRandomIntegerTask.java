package executor.integer_generator;

import java.util.Random;

public class GenerateRandomIntegerTask implements Runnable {

    private final Random random = new Random();

    @Override
    public void run() {
        try {
            Thread.sleep(100);
            int randomInt = random.nextInt(1000);
            System.out.println("Generate Random Integer = " + randomInt);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
