# Hangman

A classic Hangman word guessing game written in Java (procedural style).

## How to Play

1. Run the program
2. Press **N** to start a new game or **E** to exit
3. Guess the hidden word one **uppercase letter** at a time
4. You have **6 attempts** before the hangman is complete

```
  +---+
  |   |
  O   |
 /|\  |
 / \  |
      |
=========
```

## Rules

- Enter one uppercase letter per turn (e.g. `A`, `B`, `C`)
- Correct guess → the letter is revealed in the word
- Wrong guess → one part of the hangman is drawn
- Guess all letters before running out of attempts to win

## Project Structure

```
hangman/
├── src/
│   └── Main.java      # Game logic
└── words.txt          # Word list (IT / general vocabulary)
```

## Requirements

- Java 21+

## Run

```bash
javac src/Main.java
java -cp src Main
```

## Example

```
_ _ _ _ _ _ _
Used letters: A T
Print letter what you wanna guess: E

  +---+
  |   |
  O   |
      |
      |
      |
=========
You have 5 out of 6 mistakes left.
```
