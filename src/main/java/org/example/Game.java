package org.example;

import java.util.*;
import java.io.PrintWriter;

public class Game {
    private final Dict dict;
    private final String secret;
    private final PrintWriter log; //если удаляю, то нету зависимости, поэтому оставлю
    private int attempts = 6;
    private boolean won = false;
    private final List<String> guesses = new ArrayList<>();
    private final List<String> hints = new ArrayList<>();
    private final Random random = new Random();

    public Game(Dict dict, PrintWriter log) {
        this.dict = dict;
        this.log = log;
        this.secret = dict.randomWord();
        log.println("Загаданное слово: " + secret);
    }

    public String guess(String word) {
        if (won) throw new AlreadyException();
        if (!dict.contains(word)) throw new NoException();
        String hint = hint(word, secret);
        guesses.add(word);
        hints.add(hint);
        attempts--;
        if (word.equals(secret)) won = true;
        return hint;
    }

    public String getHint() {
        List<String> possible = dict.getPossible(guesses, hints);
        if (possible.isEmpty()) return "ошибка (нет слов)";
        String h = possible.get(random.nextInt(possible.size()));
        while (guesses.contains(h) && possible.size() > 1)
            h = possible.get(random.nextInt(possible.size()));
        return h;
    }

    private String hint(String g, String s) {
        StringBuilder sb = new StringBuilder("*****");
        boolean[] used = new boolean[5];
        boolean[] present = new boolean[5];

        for (int i = 0; i < 5; i++) {
            if (g.charAt(i) == s.charAt(i)) {
                sb.setCharAt(i, '+');
                used[i] = true;
                present[i] = true;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (sb.charAt(i) == '+') continue;
            char c = g.charAt(i);
            for (int j = 0; j < 5; j++) {
                if (!used[j] && s.charAt(j) == c) {
                    sb.setCharAt(i, '^');
                    used[j] = true;
                    present[i] = true;
                    break;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!present[i]) {
                sb.setCharAt(i, '-');
            }
        }
        return sb.toString();
    }

    public boolean hasAttempts() { return attempts > 0; }
    public boolean isWon() { return won; }
    public String getSecret() { return secret; }
}