package recursion;

import java.io.*;

public class HanoiTowers {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

                int n = Integer.parseInt(reader.readLine());
                StringBuilder builder = new StringBuilder();
                move(writer, builder, n, 1, 3);
        }
    }

    private static void move(BufferedWriter writer, StringBuilder builder, int n, int from, int to) throws IOException {
        if (n == 1) {
            builder.append(n).append(" ").append(from).append(" ").append(to).append("\n");
            writer.write(builder.toString());
            builder.delete(0, builder.length());
            return;
        }

        int number = 6 - from - to;
        move(writer, builder, n - 1, from, number);

        builder.append(n).append(" ").append(from).append(" ").append(to).append("\n");
        writer.write(builder.toString());
        builder.delete(0, builder.length());

        move(writer, builder, n - 1, number, to);
    }
}
