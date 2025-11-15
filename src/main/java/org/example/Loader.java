package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Loader {
    private final PrintWriter log;

    public Loader(PrintWriter log) {
        this.log = log;
    }

    public Dict load(String path) throws IOException {
        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim().toLowerCase().replace('ё', 'е');
                if (line.length() == 5 && line.matches("[а-я]+")) {
                    list.add(line);
                }
            }
        }

        if (list.isEmpty()) throw new IOException("Нет 5-буквенных слов");
        log.println("Загружено слов: " + list.size());
        return new Dict(list);
    }
}