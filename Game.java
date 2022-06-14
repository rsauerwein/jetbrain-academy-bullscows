package bullscows;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
    private final Scanner scanner = new Scanner(System.in);
    private String secret;
    private int turnNumber = 1;
    private boolean isRunning;

    public void start() {
        String userInput;

        System.out.println("Please, enter the secret code's length:");
        userInput = scanner.nextLine();
        int length;
        int maxSymbols;

        try {
            length = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", userInput);
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");
        userInput = scanner.nextLine();
        try {
            maxSymbols = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            System.out.printf("Error: \"%s\" isn't a valid number.", userInput);
            return;
        }

        if (length <= 0) {
            System.out.println("Error: Length must be greater than 0!");
            return;
        }

        if (length > maxSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, maxSymbols);
            return;
        }

        if (maxSymbols > 36) {
            System.out.printf("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        generateSecret(length, maxSymbols);
        System.out.println("Okay, let's start a game!");

        isRunning = true;
        while (isRunning) {
            System.out.printf("Turn %d:\n", turnNumber);
            grader(scanner.nextLine());
            turnNumber++;
        }
    }

    private void grader(String input) {
        int bullCount = 0;
        int cowCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (secret.charAt(i) == c) {
                bullCount++;
            } else if (secret.indexOf(c) >= 0) {
                cowCount++;
            }
        }

        if (bullCount > 0 && cowCount > 0) {
            System.out.printf("Grade: %s bull(s) and %s cow(s).", bullCount, cowCount);
        } else if (bullCount > 0) {
            System.out.printf("Grade: %s bull(s).", bullCount);
        } else if (cowCount > 0) {
            System.out.printf("Grade: %s cow(s).", cowCount);
        } else {
            System.out.printf("Grade: None.");
        }

        System.out.println();

        if (bullCount == secret.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            isRunning = false;
        }
    }

    private void generateSecret(int length, int maxSymbols) {
        StringBuilder secret = new StringBuilder();
        char[] hidden = new char[length];
        Arrays.fill(hidden, '*');
        System.out.println();
        System.out.print("The secret is prepared: " + new String(hidden) + " ");

        Random random = new Random();
        if (maxSymbols <= 10) {
            System.out.print("(0-" + (maxSymbols - 1) + ").");
        } else {
            System.out.print("(0-" + maxSymbols + ", " + "a-" + (char) (96 + maxSymbols -10) + ").");
        }

        while (secret.length() < length) {
            int randomNumber = random.nextInt(maxSymbols);
            char currentSymbol;
            if (randomNumber > 9) {
                currentSymbol = (char) ('a' + randomNumber % 10);
            } else {
                currentSymbol = Character.forDigit(randomNumber, 10);
            }

            if (secret.indexOf(String.valueOf(currentSymbol)) == -1) {
                secret.append(currentSymbol);
            }
        }

        this.secret = secret.toString();
    }
}
