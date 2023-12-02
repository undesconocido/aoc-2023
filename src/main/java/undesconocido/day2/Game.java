package undesconocido.day2;

import java.util.Arrays;
import java.util.List;

record Game(int id, List<Draw> draws) {
    public static Game of(final String line) {
        final var splitByColumn = line.split(":");     // [Game X, {rest}]
        final var idValue = Integer.parseInt(splitByColumn[0].replaceAll("\\D", "").trim()); // clear Game and fetch value
        final var drawList = Arrays.stream(splitByColumn[1].split(";")) // [draw1, draw2, ...]
                .map(Draw::of)
                .toList();

        return new Game(idValue, drawList);
    }

    public boolean isValid() {
        return draws.stream().allMatch(draw -> draw.red() <= 12 && draw.green() <= 13 && draw.blue() <= 14);
    }

    public int cube() {
        final int red = draws.stream()
                .mapToInt(Draw::red)
                .max()
                .orElse(0);

        final int green = draws.stream()
                .mapToInt(Draw::green)
                .max()
                .orElse(0);

        final int blue = draws.stream()
                .mapToInt(Draw::blue)
                .max()
                .orElse(0);

        return red * green * blue;
    }
}

record Draw(int red, int blue, int green) {
    public static Draw of(final String drawString) {
        int red = 0, green = 0, blue = 0;

        for (var numberAndColor : drawString.split(",")) {
            int cubes = Integer.parseInt(numberAndColor.trim().split(" ")[0]);
            if (numberAndColor.contains("red") && (cubes > red)) {
                red = cubes;
            } else if (numberAndColor.contains("green") && (cubes > green)) {
                green = cubes;
            } else if (numberAndColor.contains("blue") && (cubes > blue)) {
                blue = cubes;
            }
        }

        return new Draw(red, blue, green);
    }
}