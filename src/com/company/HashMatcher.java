package com.company;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HashMatcher implements Callable<String> {

    private final String attempt;
    private final String hash;

    public HashMatcher(String attempt, String hash) {
        this.attempt = attempt;
        this.hash = hash;
    }

    @Override
    public String call() throws Exception {

        var attemptHash = md5(attempt);

        if (hash.equals(attemptHash)) {
            return attempt;
        }

        return "";
    }

    public String md5(String data) throws NoSuchAlgorithmException {

        var md5 = MessageDigest.getInstance("MD5");
        var digest = md5.digest(data.getBytes(UTF_8));

        return String.format("%032x", new BigInteger(1, digest));
    }
}
