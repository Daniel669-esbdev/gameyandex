package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    private static final String DICT_FILE = "dictionary.txt";
    private static final String LOG_FILE = "game.log";

    public static void main(String[] args) {
        PrintWriter log = null;
        try {
            log = new PrintWriter(new OutputStreamWriter(new FileOutputStream(LOG_FILE), "UTF-8"));
            log.println("=== Wordle запущен: " + java.time.LocalDateTime.now() + " ===");

            Loader loader = new Loader(log);
            Dict dict = loader.load(DICT_FILE);

            Game game = new Game(dict, log);

            Scanner sc = new Scanner(System.in, "UTF-8");
            System.out.println("WORDLE на русском яязыке — 5 букв, 6 попыток");
            System.out.println("Нажми Enter — и получишь подсказку\n");

            while (game.hasAttempts() && !game.isWon()) {
                System.out.print("> ");
                String input = sc.nextLine().trim().toLowerCase().replace('ё', 'е');

                if (input.isEmpty()) {
                    System.out.println("Подсказка: " + game.getHint() + "\n");
                    continue;
                }
                if (input.length() != 5 || !input.matches("[а-я]+")) {
                    System.out.println("Только 5 букв!\n");
                    continue;
                }

                try {
                    String hint = game.guess(input);
                    System.out.println(input);
                    System.out.println(hint + "\n");
                } catch (NoException e) {
                    System.out.println("Слово не-из словаря!\n");
                } catch (AlreadyException e) {
                    System.out.println("Ты уже выйграл и прошел игру!\n");
                }
            }

            System.out.println(game.isWon() ? "ООО да, ты выйгал!!!" : "Поражение :(");
            System.out.println("Загаданное слово: " + game.getSecret());

            log.println("Игра закончена.Победил: " + game.isWon());

        } catch (Exception e) {
            if (log != null) {
                log.println("Ошибка: " + e.getMessage());
                e.printStackTrace(log);
            }
            System.err.println("Ошибка запуска: " + e.getMessage());
        } finally {
            if (log != null) log.close();
        }
    }
}