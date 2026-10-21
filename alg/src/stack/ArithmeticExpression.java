package stack;

// -6 + 2 * ((21 - 1) * 2 - 20) / 4   // 4
//List<String> postfix = List.of("-6", "2", "21", "1", "-", "2", "*", "20", "-", "*", "4", "/", "+");

import java.io.*;
import java.util.*;

public class ArithmeticExpression {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String data = reader.readLine();

            Deque<Integer> stack = new ArrayDeque<>();
            String postfix = postfixNotation(data);

            boolean isTrue;
            if (postfix.equals("WRONG")) {
                writer.write(postfix);
            } else {
                isTrue = getResult(List.of(postfix.split(" ")), stack);
                writer.write(isTrue && stack.size() == 1 ? String.valueOf(stack.pop()) : "WRONG");
            }
        }
    }

    private static String postfixNotation(String data) {

        Set<Character> set = Set.of('+', '-', '*', '/', ' ', '(', ')');
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char checkChar = data.charAt(i);
            if (!Character.isDigit(checkChar) && !set.contains(checkChar)) return "WRONG";

            if (Character.isDigit(checkChar)) {
                builder.append(checkChar);
                if (i == data.length() - 1 || (i < data.length() - 1 && !Character.isDigit(data.charAt(i + 1)))) {
                    builder.append(' ');
                }
            } else if (checkChar == '(') {
                stack.push(checkChar);
            } else if (checkChar == ')') {
                if (stack.isEmpty()) return "WRONG";
                while (true) {
                    char cur = stack.pop();
                    if (cur != '(') {
                        builder.append(cur).append(' ');
                    } else {
                        break;
                    }
                    if (stack.isEmpty()) return "WRONG";
                }
            } else if (checkChar == '*' || checkChar == '/') {
                if (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/')) {
                    changeStackHead(stack, builder, checkChar);
                } else {
                    stack.push(checkChar);
                }
            } else if (checkChar == '+' || checkChar == '-') {
                if (stack.isEmpty() || stack.peek() == '(') {
                    stack.push(checkChar);
                } else {
                    changeStackHead(stack, builder, checkChar);
                }
            }
        }

        while (!stack.isEmpty()) {
            char cur = stack.pop();
            if (cur == '(') return "WRONG";
            builder.append(cur).append(' ');
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /*private static String postfixNotation(String data) {

        Set<Character> set = Set.of('+', '-', '*', '/', ' ', '(', ')');
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < data.length(); i++) {
            char checkChar = data.charAt(i);
            if (!Character.isDigit(checkChar) && !set.contains(checkChar)) return "WRONG";

            if (Character.isDigit(checkChar)) {
                builder.append(checkChar);
                if (i == data.length() - 1 || (i < data.length() - 1 && !Character.isDigit(data.charAt(i + 1)))) {
                    builder.append(' ');
                }
            } else if (checkChar == '(') {
                stack.push(checkChar);
            } else if (checkChar == ')') {
                if (stack.isEmpty()) return "WRONG";
                while (true) {
                    char cur = stack.pop();
                    if (cur != '(') {
                        builder.append(cur).append(' ');
                    } else {
                        break;
                    }
                    if (stack.isEmpty()) return "WRONG";
                }
            } else if (checkChar == '*' || checkChar == '/') {
                if (!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/')) {
                    changeStackHead(stack, builder, checkChar);
                } else {
                    stack.push(checkChar);
                }
            } else if (checkChar == '+' || checkChar == '-') {
                if (stack.isEmpty() || stack.peek() == '(') {
                    stack.push(checkChar);
                } else {
                    changeStackHead(stack, builder, checkChar);
                }
            }
        }

        while (!stack.isEmpty()) {
            char cur = stack.pop();
            if (cur == '(') return "WRONG";
            builder.append(cur).append(' ');
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }*/

    private static void changeStackHead(Deque<Character> stack, StringBuilder builder, char checkChar) {
        char cur = stack.pop();
        builder.append(cur).append(' ');
        stack.push(checkChar);
    }

    private static boolean getResult(List<String> data, Deque<Integer> stack) {

        for (String s : data) {
            int currentInt;
            Integer firstInt;
            Integer secondInt;

            try {
                currentInt = Integer.parseInt(s);
                stack.push(currentInt);
            } catch (Exception e) {
                if (stack.size() < 2) return false;
                secondInt = stack.pop();
                firstInt = stack.pop();
                switch (s) {
                    case "+" -> {
                        currentInt = firstInt + secondInt;
                        stack.push(currentInt);
                    }
                    case "-" -> {
                        currentInt = firstInt - secondInt;
                        stack.push(currentInt);
                    }
                    case "*" -> {
                        currentInt = firstInt * secondInt;
                        stack.push(currentInt);
                    }
                    case "/" -> {
                        if (secondInt != 0) {
                            currentInt = firstInt / secondInt;
                            stack.push(currentInt);
                        } else {
                            return false;
                        }
                    }
                    default -> {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
