package stack;

import java.io.*;
import java.util.Stack;

public class CorrectBracketSequence {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String str = reader.readLine();

            boolean result = isTrue(str);

            if (result) {
                writer.write("yes");
            } else {
                writer.write("no");
            }
        }
    }

    private static boolean isTrue(String str) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char checkChar = str.charAt(i);
            if (checkChar == '(' || checkChar == '[' || checkChar == '{') {
                stack.push(checkChar);
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else {
                    char charFromStack = stack.pop();
                    if (checkChar == ')' && charFromStack != '(') {
                        return false;
                    } else if (checkChar == ']' && charFromStack != '[') {
                        return false;
                    } else if (checkChar == '}' && charFromStack != '{') {
                        return false;
                    }
                }
            }
        }
        return stack.isEmpty();
    }
}
