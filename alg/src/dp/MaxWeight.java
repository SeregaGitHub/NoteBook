package dp;

import java.io.*;
import java.util.Arrays;

public class MaxWeight {
    public static void main(String[] args) throws IOException {
        // Выведите единственное число — максимальную массу, которую можно забрать с собой
/*
5 19
10 10 7 7 4

18
*/
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int countNumbers = data[0];
            int maxWeight = data[1];

            int[] gold = readNumbersToArray(reader);

            int result = getResult(gold, maxWeight);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(int[] gold, int maxWeight) {

        int[] dp = new int[maxWeight + 1];

        for (int g : gold) {
            for (int j = maxWeight; j >= g; j--) {
                dp[j] = Math.max(dp[j], dp[j - g] + g);
            }
        }

        return dp[maxWeight];
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
