package dp;

import java.io.*;
import java.util.Arrays;

/*
Конь может перемещаться на две клетки вниз и одну клетку вправо или на одну клетку вниз и две клетки вправо.
result — количество способов добраться конём до правого нижнего угла доски.
Путь нулевой длины также считается корректным маршрутом.

3 2
1

31 34
293930

 */

public class HorseStep {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbersToArray(reader);
            int n = data[0];
            int m = data[1];

            int result = getResult(n, m);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(int n, int m) {

        int[][] dp = new int[n][m];
        dp[0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (i - 1 >= 0 && j - 2 >= 0) {
                    dp[i][j] += dp[i - 1][j - 2];
                }
                if (i - 2 >= 0 && j - 1 >= 0) {
                    dp[i][j] += dp[i - 2][j - 1];
                }
            }
        }

        return dp[n - 1][m  -1];
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
