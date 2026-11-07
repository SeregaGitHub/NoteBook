package dp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LongestCommonSubsequence {

// Наибольшая общая подпоследовательность (НОП) с восстановлением ответа
/*
5
2 4 4 1 3
6
1 4 4 4 2 1

3
4 4 1
*/

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int n = Integer.parseInt(reader.readLine());
            int[] firstData = readNumbersToArray(reader, n);
            int m = Integer.parseInt(reader.readLine());
            int[] secondData = readNumbersToArray(reader, m);

            int[][] dp = findMaxCommonSubsequenceDp(n, firstData, m, secondData);

            writer.write(String.valueOf(dp[n][m]));
            writer.write("\n");

            List<Integer> answer = getAnswer(dp, n, m, firstData, secondData);
            for (int i = answer.size() - 1; i >= 0; i--) {
                writer.write(String.valueOf(answer.get(i)));
                writer.write(" ");
            }
        }
    }

    private static int[][] findMaxCommonSubsequenceDp(int n, int[] firstData, int m, int[] secondData) {

        int[][] dp = new int[n + 1][m + 1];

        // n=5   m=6
        //   0 1 4 4 4 2 1
        //   0 0 0 0 0 0 0
        // 2 0 0 0 0 0 1 1
        // 4 0 0 1 1 1 1 1
        // 4 0 0 1 2 2 2 2
        // 1 0 1 1 2 2 2 3
        // 3 0 1 1 2 2 2 3

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (firstData[i] != secondData[j]) {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                } else {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }

        return dp;
    }

    private static List<Integer> getAnswer(int[][] dp, int n, int m, int[] firstData, int[] secondData) {

        List<Integer> answer = new ArrayList<>();

        while (dp[n][m] != 0) {
            if (firstData[n] != secondData[m]) {
                if (dp[n][m - 1] > dp[n - 1][m]) {
                    m--;
                } else {
                    n--;
                }
            } else {
                answer.add(firstData[n]);
                n--;
                m--;
            }
        }

        return answer;
    }

    private static int[] readNumbersToArray(BufferedReader reader, int len) throws IOException {

        String[] str = reader.readLine().split(" ");
        int[] array = new int[len + 1];

        for (int i = 0; i < len; i++) {
            array[i + 1] = Integer.parseInt(str[i]);
        }
        
        return array;
    }
}
