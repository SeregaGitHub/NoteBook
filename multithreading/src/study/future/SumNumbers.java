package study.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//  Сумма чисел от 1 до 1000000000
public class SumNumbers {
    private static long value = 1_000_000_000L;
    private static long sum = 0L;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Long> list = new ArrayList<>();
        long valueDividedBy10 = value / 10;

        for (int i = 0; i < 10; i++) {
            long from = valueDividedBy10 * i + 1;
            long to = valueDividedBy10 * (i + 1);

            PartialSum task = new PartialSum(from, to);
            Future<Long> futurePartSum = executorService.submit(task);
            list.add(futurePartSum.get());

            sum = list.stream()
                            .reduce(sum, Long::sum);

            executorService.shutdown();
            System.out.println("Total sum = " + sum);
        }
    }
}

class PartialSum implements Callable<Long> {
    long from;
    long to;
    long localSum;

    public PartialSum(long from, long to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Long call() {
        for (long i = from; i <= to; i++) {
            localSum += i;
        }
        System.out.println("Sum from " + from + " to " + to + " = " + localSum);
        return localSum;
    }
}