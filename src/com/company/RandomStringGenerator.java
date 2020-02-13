package com.company;

import java.util.Objects;
import java.util.Random;

public class RandomStringGenerator {

    private final Random random;
    private final char[] symbols;

    public RandomStringGenerator(Random random, String symbols) {
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
    }

    public String nextString(int length) {
        var buf = new char[length];

        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public String nextString(int min, int max) {

        var buf = new char[nextInt(min, max)];

        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

    private Integer nextInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
}
