import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static String[] HANGMAN = {
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

    private static Scanner scanner = new Scanner(System.in);
    private static Random r = new Random();

    public static void main(String[] args) throws IOException {
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

            startGame();
        }
    }

    public static void startGame() throws IOException {
        int currErrors = 0;
        int currGuessed = 0;
        List<String> usedLetters = new ArrayList<>();

        List<String> words = Files.readAllLines(Path.of("words.txt"));

        String[] word = chooseWord(words).split("");

        String[] field = initField(word.length);

        do {
            printGameState(field, usedLetters);

            String currSymbol = readLetter();
            System.out.println();
            boolean contain = Arrays.asList(word).contains(currSymbol);
            if (usedLetters.contains(currSymbol)) {
                System.out.println("You already guessed this letter, try again");
            } else if (contain) {
                replaceStars(field, word, currSymbol);
                usedLetters.add(currSymbol);
                currGuessed++;
                if (currGuessed == countOfUniqSymb(word)) break;
            } else {
                usedLetters.add(currSymbol);
                handleWrongGuess(currErrors);
                currErrors++;

            }

        } while (currErrors < MAX_ERRORS);

        printResult(currGuessed == countOfUniqSymb(word), word);
    }

    static void printGameState(String[] field, List<String> usedLetters) {
        System.out.println(String.join("", field));
        if (!usedLetters.isEmpty()) {
            System.out.println("You already guessed letters: " + String.join(" ", usedLetters));
        }
    }

    static String chooseWord(List<String> words) {
        int wordIdx = r.nextInt(words.size());
        return words.get(wordIdx);
    }

    static void replaceStars(String[] field, String[] word, String symb) {
        for (int i = 0; i < word.length; i++) {
            if (Objects.equals(word[i], symb)) {
                field[i] = symb;
            }
        }
    }

    static int countOfUniqSymb(String[] word) {
        Set<String> set = new HashSet<>(Arrays.asList(word));
        return set.size();
    }

    static String[] initField(int n) {
        String[] field = new String[n];
        Arrays.fill(field, "*");

        return field;
    }

    static void printResult(boolean guessed, String[] word) {
        if (guessed) {
            System.out.println("Congrats you guessed the word!!!!");
            System.out.println("Word is " + String.join("", word));
        } else {
            System.out.println("Sorry you loss the game((((((");
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