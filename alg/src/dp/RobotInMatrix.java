package dp;

import java.io.*;
import java.util.Arrays;

public class RobotInMatrix {
    public static void main(String[] args) throws IOException {
        //  Роботу надо дойти от левого нижнего до правого верхнего угла, а передвигаться он умеет только вверх и вправо
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int[] data = readLine(reader);
            int n = data[0];
            int m = data[1];

            int[][] matrix = createMatrix(reader, n, m);

            int[][] dp = getResult(matrix, n, m);
            String result = getWay(dp, n, m);

            writer.write(String.valueOf(dp[0][m]));
            writer.write("\n");
            writer.write(result);
        }
    }

    private static String getWay(int[][] dp, int n, int m) {

        StringBuilder builder = new StringBuilder();

        int i = 0;
        int j = m;

        while (i != n - 1 || j != 1) {
            // find way to up/right
            // from  up/right -> down/left
            if (j > 1 && dp[i][j - 1] >= dp[i + 1][j]) {
                builder.append('R');
                j--;
            } else {
                builder.append('U');
                i++;
            }
        }
        return builder.reverse().toString();
    }

    private static int[][] getResult(int[][] matrix, int n, int m) {

        int[][] dp = new int[n + 1][m + 1];

        // from  down/left -> up/right
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = matrix[i][j] + Math.max(dp[i][j - 1], dp[i + 1][j]);
            }
        }

        return dp;
    }

    private static int[][] createMatrix(BufferedReader reader, int n, int m) throws IOException {

        int[][] matrix = new int[n + 1][m + 1];

        for (int i = 0; i < n; i++) {
            String line = reader.readLine();
            for (int j = 1; j <= m; j++) {
                matrix[i][j] = line.charAt(j - 1) - '0';
            }
        }

        return matrix;
    }

    private static int[] readLine(BufferedReader reader) throws IOException {
        String str = reader.readLine();
        if (str == null || str.isBlank()) return new int[0];
        return Arrays.stream(str.split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
