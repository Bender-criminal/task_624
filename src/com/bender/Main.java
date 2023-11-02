package com.bender;

import java.io.*;
import java.util.HashSet;

public class Main {
    private static PrintStream out = System.out;
    private static final String FILE_NAME = "C:\\work_file.txt";
    private static HashSet<String> permutations = new HashSet<>();

    public static void main(String[] args) {
        String inputString = null;
        //подготавливаем объект для чтения из файла. Использую try с ресурсами (объект освободится сам)
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_NAME))) {
            //чтение + валидация
            inputString = bufferedReader.readLine();
            int length = inputString.length();
            if (length < 2 || length > 8 || !isLatinAndDigits(inputString)) {
                out.println("Входные данные в файле не соответствуют требованиям: длина строки 2-8 символов латинского алфавита или цифр");
                System.exit(0);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        getPermutations(inputString.toCharArray(), 0);

        //подготавливаем объект для записи в файл. Использую try с ресурсами (объект освободится сам)
        try (FileWriter fileWriter = new FileWriter(FILE_NAME, false)) {
            for (String permutation : permutations) {
                fileWriter.write(permutation + "\n");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        out.println("Перестановки найдены. Проверьте файл " + FILE_NAME);
    }

    /**
     * метод проверки строки на буквы латинского алфавита и цифры
     * сделал простой проверкой всех символов стороки. Гораздо красивее и эффективниее использовать регулярные выражения или лямбда-выражения, если вы в курсе что это
     *
     * @param s - строка
     * @return результат проверки
     */
    private static boolean isLatinAndDigits(String s) {
        if (s == null) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z') && !(c >= '0' && c <= '9')) {
                return false;
            }
        }

        return true;
    }

    /**
     * Вспомогательная функция для замены двух символов в массиве символов
     * @param chars - массив символов
     * @param i - индекс заменяемого символа
     * @param j - индекс заменяющего символа
     */
    private static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }

    /**
     * Рекурсивная функция для генерации всех перестановок строки
     * @param chars - массив символов
     * @param currentIndex - индекс рекурсии
     */
    private static void getPermutations(char[] chars, int currentIndex) {
        if (currentIndex == chars.length - 1) {
            //для сбора перестановок использую интерфейс Set, чтобы исключить повторы
            permutations.add(String.valueOf(chars));
        }

        for (int i = currentIndex; i < chars.length; i++) {
            swap(chars, currentIndex, i);
            getPermutations(chars, currentIndex + 1);
            swap(chars, currentIndex, i);
        }
    }
}
