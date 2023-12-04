package undesconocido.day4;

import undesconocido.Day;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;
import static org.assertj.core.api.Assertions.assertThat;

public class Day4 implements Day {
    private static final Path INPUT = Path.of("src/main/resources/day4/input.txt");
    private static final Path TEST_INPUT = Path.of("src/main/resources/day4/test.txt");

    public static void main(String[] args) {
        new Day4().run();
    }

    @Override
    public String test() throws IOException {
        final var lines = getLines(TEST_INPUT);

        final var total = lines.stream()
                .map(Card::of)
                .map(Card::points)
                .mapToInt(Integer::intValue)
                .sum();

        assertThat(total).isEqualTo(13);

        return String.valueOf(total);
    }

    @Override
    public String part1() throws IOException {
        final var lines = getLines(INPUT);

        final var total = lines.stream()
                .map(Card::of)
                .map(Card::points)
                .mapToInt(Integer::intValue)
                .sum();

        assertThat(total).isEqualTo(21959);

        return String.valueOf(total);
    }

    @Override
    public String test2() throws IOException {
        final var cards = getLines(TEST_INPUT).stream()
                .map(Card::of)
                .collect(Collectors.toList());

        part2Logic(cards);

        assertThat(cards.size()).isEqualTo(30);

        return String.valueOf(cards.size());
    }

    @Override
    public String part2() throws IOException {
        final var cards = getLines(INPUT).stream()
                .map(Card::of)
                .collect(Collectors.toList());

        part2Logic(cards);

        assertThat(cards.size()).isEqualTo(5132675);

        return String.valueOf(cards.size());
    }

    private void part2Logic(final List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            final var currentCard = cards.get(i);
            final var winnersFound = currentCard.winningMatches();
            if (winnersFound.isEmpty()) {
                continue;
            }
            var id = currentCard.id;
            range(id + 1, id + 1 + winnersFound.size()).forEachOrdered(j -> cards.add(findCard(cards, j)));
        }
    }

    private Card findCard(final List<Card> cards, final int id) {
        return cards.stream()
                .filter(card -> card.id == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Card with id " + id + " not found"));
    }

    public record Card(int id, Set<Integer> numbers, Set<Integer> winners) {
        static Card of(final String line) {
            final var cardFromValueSplit = line.split(":");
            final var id = Integer.parseInt(cleanWhiteSpaces(cardFromValueSplit[0]).split(" ")[1]);
            final var values = cardFromValueSplit[1].strip().split("\\|");
            final var numbers = Arrays.stream(cleanWhiteSpaces(values[0]).split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
            final var winningNumbers = Arrays.stream(cleanWhiteSpaces(values[1]).split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());

            return new Card(id, numbers, winningNumbers);
        }

        private static String cleanWhiteSpaces(final String line) {
            return line.strip().replaceAll("\\s+", " ");
        }

        int points() {
            final var numberOfMatches = winningMatches().size();
            return numberOfMatches == 0 ? 0 : (int) Math.pow(2, (double) numberOfMatches - 1);
        }

        private Set<Integer> winningMatches() {
            return numbers.stream()
                    .filter(winners::contains)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }
}
