package undesconocido.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main1P2 {
    // Solution: 54719

    private static final String INPUT_TXT = "src/main/resources/day1/input.txt";

    public static void main(String[] args) throws IOException {
        try (final var lines = Files.lines(Paths.get(INPUT_TXT))) {
            int total = lines
                    .peek(System.out::println)
                    .map(Main1P2::convertWordsToNumbers)
                    .map(Main1P2::eraseAllLetters)
                    .mapToInt(Main1P2::getIntValue)
                    .sum();

            System.out.println("Total: " + total);
        } catch (IOException e) {
            System.err.println("IOException when trying to read the file: " + e.getMessage());
        }
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
