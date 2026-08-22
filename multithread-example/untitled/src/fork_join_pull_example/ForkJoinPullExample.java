package fork_join_pull_example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPullExample {
    public static void main(String[] args) {
        int arraySize = 1_000_000_000;
        int[] array = new int[arraySize];

        fillArray(array);

        long start = System.currentTimeMillis();
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            long calculatedSum = forkJoinPool.invoke(new SumTask(array, 0, array.length));
            System.out.println("Sum: " + calculatedSum);
            long gaussSum = gaussSum(arraySize);
            System.out.println("Gauss sum: " + gaussSum);

            if (gaussSum == calculatedSum) {
                System.out.println("Result is correct");
            }

            long end = System.currentTimeMillis();
            long duration = end - start;
            System.out.println("Time elapsed: " + duration + " ms");
        }
    }

    private static long gaussSum(int n) {
        return (long) n * (n + 1) / 2;
    }

    private static void fillArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }
    }
}

class SumTask extends RecursiveTask<Long> {

    private final Integer THRESHOLD = 1000;

    private final int [] array;
    private final int start;
    private final int end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);
            leftTask.fork();
            long rightResult = rightTask.compute();
            long leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }
}