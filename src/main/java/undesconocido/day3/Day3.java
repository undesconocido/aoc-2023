package undesconocido.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3 {
    private static final String INPUT_TXT = "src/main/resources/day3/input.txt";
    //private static final String INPUT_TXT = "src/main/resources/day3/test.txt";

    public static void main(final String[] args) {
        try {
            final var lines = Files.readAllLines(Paths.get(INPUT_TXT));

            var map = new HashMap<Integer, List<Number>>();
            var lineCounter = new AtomicInteger(0);
            var pattern = Pattern.compile("\\d+");

            lines.forEach(line -> {
                var list = new ArrayList<Number>();
                var matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group().isBlank()) {
                        continue;
                    }
                    int start = matcher.start();
                    int end = matcher.end();
                    var number = new Number(lineCounter.get(), start, end, Integer.parseInt(line.substring(start, end)));
                    list.add(number);
                }
                map.put(lineCounter.get(), list);
                lineCounter.incrementAndGet();
            });

            var parts = new ArrayList<Number>();

            map.forEach((key, value) -> {
                value.forEach(number -> {
                    if (number.isAdjacentToSymbol(lines)) {
                        System.out.println("Adjacent: " + number);
                        parts.add(number);
                    }
                });

                System.out.println("Original Line: " + lines.get(key));
                System.out.println("Line: " + key + ", Value: " + value);
                System.out.println();
            });

            parts.forEach(System.out::println);
            System.out.println("Part 1 Sum: " + parts.stream().mapToInt(Number::value).sum()); // 4361
        } catch (IOException e) {
            System.err.println("Error when trying to read the file: " + e.getMessage());
        }
    }
}

record Number(int line, int start, int end, int value) {

    public boolean isAdjacentToSymbol(final List<String> lines) {
        boolean up = false, down = false, left = false, right = false;
        final var validateMaxLeft = start == 0 ? 0 : start - 1;
        final var validateMaxRight = end == lines.get(line).length() ? end : end + 1;
        if (line != 0) {
            up = checkUpperLine(lines.get(line - 1), validateMaxLeft, validateMaxRight);
        }
        if (line != lines.size() - 1) {
            down = checkLowerLine(lines.get(line + 1), validateMaxLeft, validateMaxRight);
        }
        if (start != 0) {
            left = checkLeft(lines.get(line), start);
        }
        if (end != lines.get(line).length()) {
            right = checkRight(lines.get(line), end);
        }
        System.out.println(up + " " + down + " " + left + " " + right);
        return Stream.of(up, down, left, right).anyMatch(Boolean::booleanValue);
    }

    private boolean checkRight(final String line, final int end) {
        return line.substring(end, end + 1).replace(".", "").matches("\\W+");
    }

    private boolean checkLeft(final String line, final int start) {
        return line.substring(start - 1, start).replace(".", "").matches("\\W+");
    }

    private boolean checkLowerLine(final String line, final int start, final int end) {
        return line.substring(start, end).replace(".", "").matches("\\W+");
    }

    private boolean checkUpperLine(final String line, final int start, final int end) {
        return line.substring(start, end).replace(".", "").matches("\\W+");
    }
}
