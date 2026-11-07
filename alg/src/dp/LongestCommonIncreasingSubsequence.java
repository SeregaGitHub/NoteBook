package dp;

import java.io.*;
import java.util.Arrays;

public class LongestCommonIncreasingSubsequence {

// Наибольшая общая возрастающая подпоследовательность (НОВП)

/*
8
3 8 9 1 3 1 2 1
7
1 3 1 2 8 9 1
*/

// 3:  3 8 9

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            int[] nNumbers = readNumbersToArray(reader);
            int m = Integer.parseInt(reader.readLine());
            int[] mNumbers = readNumbersToArray(reader);

            int result = getResult(n, nNumbers, m, mNumbers);
            writer.write(String.valueOf(result));
        }
    }

    private static int getResult(int n, int[] nNumbers, int m, int[] mNumbers) {

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            int maxCount = 0;
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (nNumbers[i - 1] == mNumbers[j - 1]) {
                    dp[i][j] = Math.max(dp[i][j], maxCount + 1);
                }
                if (mNumbers[j - 1] < nNumbers[i - 1]) {
                    maxCount = Math.max(maxCount, dp[i - 1][j]);
                }
            }
        }

        int result = 0;

        for (int j = 1; j <= m; j++) {
            result = Math.max(result, dp[n][j]);
        }

        return result;
    }

    private static int[] readNumbersToArray(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
