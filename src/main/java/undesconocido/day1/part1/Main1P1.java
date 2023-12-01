package undesconocido.day1.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main1P1 {
    // Solution: 55971, second try!
    private static final String INPUT_TXT = "src/main/resources/day1/input.txt";

    public static void main(String[] args) throws IOException {
        try (final var lines = Files.lines(Paths.get(INPUT_TXT))) {
            final var total = lines.mapToInt(Main1P1::getNumber)
                    .peek(System.out::println)
                    .sum();

            System.out.println("Total: " + total);
        } catch (IOException e) {
            System.err.println("IOException when trying to read the file: " + e.getMessage());
        }
    }

    private static int getNumber(final String line) {
        final int first = fetchFirstNumber(line);
        final int last = fetchLastNumber(line);
        System.out.println("Line: " + line);
        System.out.println("First: " + first + ", Last: " + last);
        return (first * 10) + last;
    }

    private static int fetchFirstNumber(final String line) {
        return line.chars()
                .filter(Character::isDigit)
                .findFirst()
                .stream()
                .map(Character::getNumericValue)
                .sum();
    }

    private static int fetchLastNumber(final String line) {
        return line.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .reduce((first, second) -> second)
                .orElse(0);
    }
}
