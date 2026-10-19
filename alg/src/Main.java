import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {

        // InsertionSort, bor, prefixFunction, Разбиение дерева, KnutMorrisPrut, Levinshtein
        // GraphMultiMST, FourSum, QuickSort -> InsertionSort, ArithmeticExpression

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] data = readNumbers(reader);

        int middleNumber = findMiddleNumber(data);
        writer.write(String.valueOf(middleNumber));

        reader.close();
        writer.close();
    }

    private static int findMiddleNumber(int[] numbers) {
        return Math.max(
                Math.min(numbers[0], numbers[1]),
                Math.min(Math.max(numbers[0], numbers[1]), numbers[2])
        );
    }

    private static int[] readNumbers(BufferedReader reader) throws IOException {
        return Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
