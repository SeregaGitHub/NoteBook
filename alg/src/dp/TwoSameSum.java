package standart;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TwoSameSum {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {
            int count = Integer.parseInt(reader.readLine());
            List<Integer> numbers = readLine(reader);

            int sum = numbers.stream().reduce(0, Integer::sum);

            if (sum % 2 != 0) {
                writer.write("False");
            } else {
                int halfSum = sum / 2;
                boolean[][] dp = new boolean[halfSum + 1][count + 1];
                fillArray(dp, count);

                for (int i = 1; i <= halfSum; i++) {
                    for (int j = 1; j <= count; j++) {
                        dp[i][j] = dp[i][j - 1];
                        if (i >= numbers.get(j - 1))
                            dp[i][j] = dp[i][j] || dp[i - numbers.get(j - 1)][j - 1];
                    }
                }
                writer.write(dp[halfSum][count] ? "True" : "False");
            }
        }
    }

    private static void fillArray(boolean[][] dp, Integer count) {
        for (int i = 0; i <= count; i++) {
            dp[0][i] = true;
        }
    }

    private static List<Integer> readLine(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
