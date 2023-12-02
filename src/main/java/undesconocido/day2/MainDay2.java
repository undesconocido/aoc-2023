package undesconocido.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainDay2 {
    private static final String INPUT_TXT = "src/main/resources/day2/input.txt";

    public static void main(String[] args) {
        try (final var lines = Files.lines(Paths.get(INPUT_TXT))) {
            final var games = lines.map(Game::of).toList();

            long part1 = games.stream()
                    .filter(Game::isValid)
                    .mapToInt(Game::id)
                    .sum();
            System.out.println("Part1 solution: " + part1);  // 2685

            long part2 = games.stream()
                    .mapToInt(Game::cube)
                    .sum();
            System.out.println("Part2 solution: " + part2);   // 83707

        } catch (IOException e) {
            System.err.println("Error when trying to read the file: " + e.getMessage());
        }
    }
}
