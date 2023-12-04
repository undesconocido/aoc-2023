package undesconocido;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public interface Day {
    default List<String> getLines(final Path path) throws IOException {
        return Files.readAllLines(path);
    }

    String test() throws IOException;

    String part1() throws IOException;

    String test2() throws IOException;

    String part2() throws IOException;

    default void run() {
        try {
            System.out.println("=========");
            System.out.println("Test 1: " + test());
            System.out.println("Part 1: " + part1());
            System.out.println("===");
            System.out.println("Test 2: " + test2());
            System.out.println("Part 2: " + part2());
            System.out.println("=========");
        } catch (IOException e) {
            System.err.println("IOException when trying to read the file: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
