package executor.integer_generator;

import java.util.Random;
import java.util.concurrent.Callable;

public class GenerateRandomIntegerCallableTask implements Callable {

    private final Random random = new Random();

    @Override
    public Object call() throws Exception {

        try {
            Thread.sleep(100);
            int randomInt = random.nextInt(1000);
            System.out.println("Generate Random Integer = " + randomInt);
            return randomInt;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
