package org.example;
import java.util.*;

public class Dict {
    private final List<String> words;
    private final Set<String> set;
    private final Random r = new Random();

    public Dict(List<String> list) {
        words = List.copyOf(list);
        set = Set.copyOf(list);
    }

    public String randomWord() {
        return words.get(r.nextInt(words.size()));
    }

    public boolean contains(String w) {
        return set.contains(w);
    }

    public List<String> getPossible(List<String> guesses, List<String> hints) {
        return words.stream()
                .filter(w -> match(w, guesses, hints))
                .toList();
    }

    private boolean match(String w, List<String> g, List<String> h) {
        for (int i = 0; i < g.size(); i++) {
            if (!check(w, g.get(i), h.get(i))) return false;
        }
        return true;
    }
    private boolean check(String w, String g, String h) {
        for (int i = 0; i < 5; i++) {
            char c = g.charAt(i);
            char mark = h.charAt(i);
            if (mark == '+' && w.charAt(i) != c) return false;
            if (mark == '^' && !w.contains(String.valueOf(c))) return false;
            if (mark == '*' && w.contains(String.valueOf(c))) {
                if (count(w, c) < required(g, h, c)) return false;
            }
        }
        return true;
    }

    private int count(String s, char c) {
        return (int) s.chars().filter(ch -> ch == c).count();
    }

    private int required(String g, String h, char c) {
        int cnt = 0;
        for (int i = 0; i < 5; i++) if (g.charAt(i) == c && h.charAt(i) != '*') cnt++;
        return cnt;
    }
}