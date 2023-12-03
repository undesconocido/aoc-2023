package undesconocido.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day1 {
    // Solution 1: 55971, second try!
    // Solution 2: 54719

    private static final String INPUT_TXT = "src/main/resources/day1/input.txt";

    public static void main(String[] args) {
        try {
            final var lines = Files.readAllLines(Paths.get(INPUT_TXT));
            final var total = lines
                    .stream()
                    .mapToInt(Day1::getNumber)
                    .peek(System.out::println)
                    .sum();

            final var total2 = lines
                    .stream()
                    .peek(System.out::println)
                    .map(Day1::convertWordsToNumbers)
                    .map(Day1::eraseAllLetters)
                    .mapToInt(Day1::getIntValue)
                    .sum();

            System.out.println("===");
            System.out.println("Part 1 Total: " + total);
            System.out.println("Part 2 Total: " + total2);
            System.out.println("===");
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

    private static int getIntValue(final String line) {
        System.out.println("Line: " + line);
        char first = line.charAt(0);
        char last = line.charAt(line.length() - 1);
        System.out.println("First: " + first + ", Last: " + last + "\n");

        return Character.getNumericValue(first) * 10 + Character.getNumericValue(last);
    }

    private static String eraseAllLetters(final String line) {
        return line.replaceAll("\\D", "");
    }

    private static String convertWordsToNumbers(String line) {
        final String[] annoyingWords = {"oneight", "twone", "eightwo", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        final String[] numericalSolution = {"18", "21", "82", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        for (int i = 0; i < annoyingWords.length; i++) {
            line = line.replace(annoyingWords[i], numericalSolution[i]);
        }

        return line;
    }
}
