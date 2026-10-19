package dp;

import java.io.*;
import java.util.Arrays;

public class StepsByStairs {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readNumbers(reader);
            int target = data[0];
            int count = data[1];

            int result = getResult(target, count);
            writer.write(String.valueOf(result));
        }
    }

    /*
        if target = 5 and count = 3:


         5:                           _2_    _6_   _13_
         4:                 _1_    _3_    _7_
         3:       _1_    _2_    _4_
         2:    _1_    _2_
         1: _1_
         0:
     */

    private static int getResult(int target, int count) {

        int[] dp = new int[target + 1];
        dp[0] = 1;

        for (int i = 1; i <= target; i++) {
            int n = 0;
            int j = i;  // always: j = i + n   "j" can be change -> i + n
            while ((n < count) && (i + n <= target)) {
                dp[j] = dp[i - 1] + dp[j];
                j++;
                n++;
            }
        }

        return dp[target];
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
