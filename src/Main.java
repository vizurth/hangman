import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static final String[] HANGMAN = {
            """
      +---+
      |   |
          |
          |
          |
          |
    =========
    """,

            """
      +---+
      |   |
      O   |
      |   |
          |
          |
    =========
    """,

            """
      +---+
      |   |
      O   |
     /|   |
          |
          |
    =========
    """,

            """
      +---+
      |   |
      O   |
     /|\\  |
          |
          |
    =========
    """,

            """
      +---+
      |   |
      O   |
     /|\\  |
     /    |
          |
    =========
    """,

            """
      +---+
      |   |
      O   |
     /|\\  |
     / \\  |
          |
    =========
    """
    };

    private static final int MAX_ERRORS = 6;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random r = new Random();

    public static void main(String[] args) throws IOException {
        List<String> words = Files.readAllLines(Path.of("words.txt"));

        String scan;
        while (true) {
            System.out.print("[N]ext word or [E]xit: ");
            scan = scanner.nextLine();
            System.out.println();
            if (Objects.equals(scan, "E")) {
                System.out.println("Good bye)");
                break;
            } else if (!Objects.equals(scan, "N")) {
                System.out.println("Please print symbols [N] or [E]");
                System.out.println();
                continue;
            }

            startGame(words);
        }
    }

    public static void startGame(List<String> words) throws IOException {
        int currErrors = 0;
        int currGuessed = 0;

        List<String> usedLetters = new ArrayList<>();

        List<String> word = Arrays.stream(chooseWord(words).split("")).toList();
        int uniqSymbols = countOfUniqSymb(word);

        List<String> field = initField(word.size());

        do {
            printGameState(field, usedLetters);

            String currSymbol = readLetter();
            System.out.println();
            boolean contain = word.contains(currSymbol);
            if (usedLetters.contains(currSymbol)) {
                System.out.println("You already guessed this letter, try again");
            } else if (contain) {
                replaceStars(field, word, currSymbol);
                usedLetters.add(currSymbol);
                currGuessed++;
                if (currGuessed == uniqSymbols) break;
            } else {
                usedLetters.add(currSymbol);
                handleWrongGuess(currErrors);
                currErrors++;

            }

        } while (currErrors < MAX_ERRORS);

        printResult(currGuessed == uniqSymbols, word);
    }

    static void printGameState(List<String> field, List<String> usedLetters) {
        System.out.println(String.join("", field));
        if (!usedLetters.isEmpty()) {
            System.out.println("You already guessed letters: " + String.join(" ", usedLetters));
        }
    }

    static String chooseWord(List<String> words) {
        int wordIdx = r.nextInt(words.size());
        return words.get(wordIdx);
    }

    static void replaceStars(List<String> field, List<String>  word, String symb) {
        for (int i = 0; i < word.size(); i++) {
            if (Objects.equals(word.get(i), symb)) {
                field.set(i, symb);
            }
        }
    }

    static int countOfUniqSymb(List<String> word) {
        Set<String> set = new HashSet<>(word);
        return set.size();
    }

    static List<String> initField(int n) {
        return new ArrayList<>(Collections.nCopies(n, "*"));
    }

    static void printResult(boolean guessed, List<String> word) {
        if (guessed) {
            System.out.println("Congrats you guessed the word!!!!");
            System.out.println("Word is " + String.join("", word));
        } else {
            System.out.println("Sorry you loss the game((((((");
            System.out.println("Word was is " + String.join("", word));
        }
    }

    static void handleWrongGuess(int currErrors) {
        System.out.println("You make error");
        System.out.println(HANGMAN[currErrors]);
        System.out.println("You have " + (MAX_ERRORS - currErrors - 1) + " out of " + MAX_ERRORS + " mistakes left.");
        System.out.println();
    }

    static String readLetter() {
        while (true) {
            System.out.print("Print letter what you wanna guess: ");

            String scan = scanner.nextLine();
            if (scan.length() == 1 && Character.isUpperCase(scan.charAt(0))) {
                return scan;
            }
            System.out.println("Enter only 1 and uppercase letters!!!!!!");
        }
    }
}