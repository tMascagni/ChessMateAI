package ai.mate.chess.ui;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.util.Scanner;

public final class Tui implements ITui {

    private static ITui instance;

    private final Scanner scanner;

    static {
        try {
            instance = new Tui();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton Tui instance!");
        }
    }

    private Tui() {
        scanner = new Scanner(System.in);
    }

    public static synchronized ITui getInstance() {
        return instance;
    }

    @Override
    public char getUserInput() {
        printArrow();
        return getChar();
    }

    @Override
    public BoardPosition getBoardPositionInput() {
        printArrow("File");
        char file = getChar();

        printArrow("Rank");
        char rank = getChar();

        return new BoardPosition(file, rank);
    }

    @Override
    public void printPressEnter() {
        printArrow();
        printMessage("Press Enter...");
        try {
            scanner.nextLine();
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    @Override
    public void printArrow() {
        printMessage(Utils.ARROW + " ");
    }

    @Override
    public void printArrow(String postfix) {
        printMessage("[" + postfix + "] " + Utils.ARROW + " ");
    }

    @Override
    public final void printStartScreen() {
        String startScreenString = "┌───────────────────────────────────────────────────────────────────────┐\n" +
                "│                                                           .::.        │\n" +
                "│                                                _()_       _::_        │\n" +
                "│                                      _O      _/____\\_   _/____\\_      │\n" +
                "│               _  _  _     ^^__      / //\\    \\      /   \\      /      │\n" +
                "│              | || || |   /  - \\_   {     }    \\____/     \\____/       │\n" +
                "│              |_______| <|    __<    \\___/     (____)     (____)       │\n" +
                "│        _     \\__ ___ / <|    \\      (___)      |  |       |  |        │\n" +
                "│       (_)     |___|_|  <|     \\      |_|       |__|       |__|        │\n" +
                "│      (___)    |_|___|  <|______\\    /   \\     /    \\     /    \\       │\n" +
                "│      _|_|_    |___|_|   _|____|_   (_____)   (______)   (______)      │\n" +
                "│     (_____)  (_______) (________) (_______) (________) (________)     │\n" +
                "│     /_____\\  /_______\\ /________\\ /_______\\ /________\\ /________\\     │\n" +
                "│                                                                       │\n" +
                "│                 ──────────────────────────────────────                │\n" +
                "│           __        __     _                                          │\n" +
                "│           \\ \\      / /___ | |  ___  ___   _ __ ___    ___             │\n" +
                "│            \\ \\ /\\ / // _ \\| | / __|/ _ \\ | '_ ` _ \\  / _ \\            │\n" +
                "│             \\ V  V /|  __/| || (__| (_) || | | | | ||  __/            │\n" +
                "│              \\_/\\_/  \\___||_| \\___|\\___/ |_| |_| |_| \\___|            │\n" +
                "│                                 _                                     │\n" +
                "│                                | |_  ___                              │\n" +
                "│                                | __|/ _ \\                             │\n" +
                "│                                | |_| (_) |                            │\n" +
                "│                                 \\__|\\___/                             │\n" +
                "│   ____  _                      __  __         _            _     ___  │\n" +
                "│  / ___|| |__    ___  ___  ___ |  \\/  |  __ _ | |_  ___    / \\   |_ _| │\n" +
                "│ | |    | '_ \\  / _ \\/ __|/ __|| |\\/| | / _` || __|/ _ \\  / _ \\   | |  │\n" +
                "│ | |___ | | | ||  __/\\__ \\\\__ \\| |  | || (_| || |_|  __/ / ___ \\  | |  │\n" +
                "│  \\____||_| |_| \\___||___/|___/|_|  |_| \\__,_| \\__|\\___|/_/   \\_\\|___| │\n" +
                "└───────────────────────────────────────────────────────────────────────┘\n";
        printMessage(startScreenString);
    }

    @Override
    public final void printMenu() {
        String menuString = "\n┌────────────────────────┐\n" +
                "│       ChessMateAI      │\n" +
                "├────────────────────────┤\n" +
                "│        Main Menu       │\n" +
                "├────────────────────────┤\n" +
                "│ (1)      Play Chess    │\n" +
                "│ (2)      Match History │\n" +
                "│                        │\n" +
                "│ (0)      Exit          │\n" +
                "└────────────────────────┘\n";
        printMessage(menuString);
    }

    @Override
    public void printUnrecognizedCommand() {
        String winString = "\n┌────────────────────────┐\n" +
                "│          Oops!         │\n" +
                "├────────────────────────┤\n" +
                "│  Unrecognized command! │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    @Override
    public final void printWin() {
        String winString = "┌────────────────────────┐\n" +
                "│         You won!       │\n" +
                "├────────────────────────┤\n" +
                "│   You managed to beat  │\n" +
                "│      ChessMateAI!      │\n" +
                "│                        │\n" +
                "│    Congratulations!    │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    @Override
    public final void printLoss() {
        String winString = "┌────────────────────────┐\n" +
                "│        You lost!       │\n" +
                "├────────────────────────┤\n" +
                "│    You were beat by    │\n" +
                "│      ChessMateAI.      │\n" +
                "│                        │\n" +
                "│ Better luck next time! │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    @Override
    public final void printBoard(Board board) {
        printMessage(board.getBoard());
    }

    private void printMessage(String msg) {
        System.out.print(msg);
    }

    private char getChar() {
        try {
            return scanner.nextLine().toLowerCase().charAt(0);
        } catch (IndexOutOfBoundsException e) {
            return ' ';
        }
    }

}