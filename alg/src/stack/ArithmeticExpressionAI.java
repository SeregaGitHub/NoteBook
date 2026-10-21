package stack;

import java.io.*;
import java.util.*;

public class ArithmeticExpressionAI {
    public static void main(String[] args) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out))) {

            String data = reader.readLine();
            Deque<Integer> stack = new ArrayDeque<>();
            String postfix = postfixNotation(data);

            if (postfix.equals("WRONG")) {
                writer.write(postfix);
            } else {
                List<String> tokens = Arrays.stream(postfix.trim().split(" "))
                        .filter(s -> !s.isEmpty())
                        .toList();

                boolean isTrue = getResult(tokens, stack);
                writer.write(isTrue && stack.size() == 1 ? String.valueOf(stack.pop()) : "WRONG");
            }
        }
    }

    private static String postfixNotation(String data) {

        Set<Character> allowedChars = Set.of('+', '-', '*', '/', ' ', '(', ')');
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder builder = new StringBuilder();
// Флаг: ожидаем ли мы число (или открывающую скобку)
        boolean expectOperand = true;

        for (int i = 0; i < data.length(); i++) {
            char checkChar = data.charAt(i);
            if (checkChar == ' ') continue; // Пропускаем пробелы
            if (!Character.isDigit(checkChar) && !allowedChars.contains(checkChar)) {
                return "WRONG";
            }
            if (Character.isDigit(checkChar)) {
// Если ждали оператор, а получили цифру (например, "1 1") — это ошибка
                if (!expectOperand) return "WRONG";
// Собираем всё многозначное число целиком
                while (i < data.length() && Character.isDigit(data.charAt(i))) {
                    builder.append(data.charAt(i));
                    i++;
                }
                i--; // Возвращаем индекс назад, так как цикл for сделает i++
                builder.append(' ');
                expectOperand = false;
            } else if (checkChar == '(') {
                if (!expectOperand) return "WRONG";
                stack.push(checkChar);
                expectOperand = true;
            } else if (checkChar == ')') {
                if (expectOperand) return "WRONG"; // Пустые скобки
                boolean foundLeft = false;
                while (!stack.isEmpty()) {
                    char cur = stack.pop();
                    if (cur == '(') {
                        foundLeft = true;
                        break;
                    }
// Заменяем маркер унарного минуса на обычный минус при выводе
                    builder.append(cur == '~' ? '-' : cur).append(' ');
                }
                if (!foundLeft) return "WRONG";
                expectOperand = false;
            } else { // Встретили оператор: +, -, *, /
                if (expectOperand) {
// Это УНАРНЫЙ оператор
                    if (checkChar == '-') {
                        builder.append("0 "); // Вставляем 0 в выходную строку
                        stack.push('~');      // Кладем в стек маркер унарного минуса
                    } else if (checkChar == '+') {
// Унарный плюс просто игнорируем
                    } else {
                        return "WRONG"; // '*' или '/' не могут быть унарными
                    }
                } else {
// Это БИНАРНЫЙ оператор
                    expectOperand = true;
                    int currentPriority = getPriority(checkChar);
                    while (!stack.isEmpty() && stack.peek() != '(' && getPriority(stack.peek()) >= currentPriority) {
                        char op = stack.pop();
                        builder.append(op == '~' ? '-' : op).append(' ');
                    }
                    stack.push(checkChar);
                }
            }
        }
        if (expectOperand) return "WRONG"; // Выражение не может заканчиваться оператором

// Опустошаем оставшийся стек

        while (!stack.isEmpty()) {

            char cur = stack.pop();
            if (cur == '(') return "WRONG";
            builder.append(cur == '~' ? '-' : cur).append(' ');

        }
        return builder.toString().trim();
    }

    private static int getPriority(char c) {

        if (c == '~') return 3; // Маркер унарного минуса имеет высший приоритет
        if (c == '*' || c == '/') return 2;
        if (c == '+' || c == '-') return 1;
        return -1;

    }

    private static boolean getResult(List<String> data, Deque<Integer> stack) {
        for (String s : data) {
            try {
                int currentInt = Integer.parseInt(s);
                stack.push(currentInt);
            } catch (Exception e) {
// Логика для всех операторов (включая "-") осталась стандартной

                if (stack.size() < 2) return false;

                int secondInt = stack.pop();
                int firstInt = stack.pop();

                switch (s) {

                    case "+" -> stack.push(firstInt + secondInt);

                    case "-" -> stack.push(firstInt - secondInt);

                    case "*" -> stack.push(firstInt * secondInt);

                    case "/" -> stack.push(firstInt / secondInt);

                    default -> {

                        return false;
                    }
                }
            }
        }
        return true;
    }
}


