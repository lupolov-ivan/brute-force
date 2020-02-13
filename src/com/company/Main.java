package com.company;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        var minLength = Integer.parseInt(args[0]);
        var maxLength = Integer.parseInt(args[1]);
        var symbols = parseRangesToSymbolsString(args[2]);
        var hash = args[3];
        var poolSize = 30;

        var generator = new RandomStringGenerator(new Random(),symbols);
        var pool = Executors.newFixedThreadPool(poolSize);

        System.out.println("Hash match search started ...");

        while (true) {

            var matcher = new HashMatcher(generator.nextString(minLength, maxLength), hash);
            var result = pool.submit(matcher);

            try {
                var maybeSecretWord = result.get();

                if(!"".equals(maybeSecretWord)) {
                    System.out.println("Secret word found: " + maybeSecretWord);
                    break;
                }

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        pool.shutdown();
    }

    private static String parseRangesToSymbolsString(String ranges) {

        var symbols = new StringBuilder();
        var rangesArray = ranges.split(":");

        for (int i = 0; i < rangesArray.length; i++) {

            var rangeLimitsArray = rangesArray[i].split("-");
            var startChar = Integer.parseInt(rangeLimitsArray[0]);
            var endChar = Integer.parseInt(rangeLimitsArray[1]);

            for (int j = startChar; j <= endChar; j++) {
                symbols.append(((char) j));
            }
        }
        return symbols.toString();
    }
}
