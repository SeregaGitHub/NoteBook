package dp;

import java.io.*;
import java.util.Arrays;

public class CountOfWaysForSum {
    public static void main(String[] args) throws IOException {
//  Нужно вывести число способов, которыми можно набрать нужную сумму
/*
5
3
3 2 1

5
*/
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int sum = Integer.parseInt(reader.readLine());
            int count = Integer.parseInt(reader.readLine());

            int[] data = readNumbersToArray(reader);

            int result = getResult(data, sum);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(int[] data, int sum) {

        int[] dp = new int[sum + 1];
        dp[0] = 1;

        for (int i : data) {
            for (int j = i; j <= sum; j++) {
                dp[j] = dp[j] + dp[j - i];
            }
        }

        return dp[sum];
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
