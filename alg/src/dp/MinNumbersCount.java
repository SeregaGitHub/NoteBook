package dp;

import java.io.*;
import java.util.Arrays;

public class MinNumbersCount {
    public static void main(String[] args) throws IOException {
        // Выведите ответ – минимальное количество чисел, сумма которых = sum; (любое число может повторяться)

/*
130
4
10 3 40 1

4
*/
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int sum = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());

            int[] numbers = readNumbersToArray(reader);
            int result = getResult(numbers, sum);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(int[] numbers, int sum) {

        int[] dp = new int[sum + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= sum; i++) {
            for (int n : numbers) {
                if (i >= n && dp[i - n] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - n] + 1);
                }
            }
        }

        return dp[sum] == Integer.MAX_VALUE ? -1 : dp[sum];
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
