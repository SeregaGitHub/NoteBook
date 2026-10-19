package recursion;

import java.io.*;

public class BracketGenerator {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            int count = Integer.parseInt(reader.readLine());
            StringBuilder builder = new StringBuilder();
            printResult(writer, builder, count, count);
        }
    }

    private static void printResult(
            BufferedWriter writer, StringBuilder builder, int open, int close
    ) throws IOException {

        if (open == 0 && close == 0) {
            writer.write(builder.toString());
            writer.write("\n");
        }

        if (open > 0) {
            builder.append('(');
            printResult(writer, builder, open - 1, close);
        }
        if (close > open) {
            builder.append(')');
            printResult(writer, builder, open, close - 1);
        }
        if (!builder.isEmpty()) builder.deleteCharAt(builder.length() - 1);
    }
}
