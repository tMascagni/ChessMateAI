package ai.mate.chess.ui.tui;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.piece.Piece;

import java.util.Scanner;

public final class Tui {

    public static final String ARROW = "»";

    private static Tui instance;

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

    public static synchronized Tui getInstance() {
        return instance;
    }

    /**************************************************
     * USER INPUT METHODS
     *
     **************************************************/
    public char getUserInput() {
        printArrow();
        return getChar();
    }

    public char getPromotionSelection() {
        return getNumericCharForPromotion("Selection");
    }

    private char[] getBoardPair(String msg) {
        char file = 'x';
        char rank = 'x';
        do {
            try {
                /* Prompt */
                printArrow(msg + ": File/Rank");

                /* Get the string */
                String input = scanner.nextLine();

                /* The string has to be of length 2, if its not, its illegal. */
                if (input.length() != 2)
                    continue;

                /* split em */
                file = Character.toLowerCase(input.charAt(0));
                rank = input.charAt(1);

            } catch (Exception ignored) {

            }
            /*
             * If its of length 2, check if the first is a alphabetic character
             * from A-H, and if the second is a numeric character from 1 to 8.
             */
        } while (!isValidFile(file) || !isValidRank(rank));

        return new char[]{file, rank};
    }

    public Piece.PlayerColor getPlayerColorInput() {
        char input;
        do {
            input = getUserInput();
        } while (input != '1' && input != '2');

        if (input == '1')
            return Piece.PlayerColor.WHITE;
        else
            return Piece.PlayerColor.BLACK;
    }


    /**************************************************
     * PRINT METHODS
     *
     **************************************************/
    public void printPressEnter() {
        printArrow();
        printMessage("Press Enter...");
        scanner.nextLine();
    }

    public void printArrow() {
        printMessage(ARROW + " ");
    }

    public void printArrow(String postfix) {
        printMessage("[" + postfix + "] " + ARROW + " ");
    }

    public void printPromotionSuccess(char selection, Piece.PlayerColor playerColor) {
        String promotedPiece = "Empty";
        switch (selection) {
            case '1':
                promotedPiece = "Queen";
                break;
            case '2':
                promotedPiece = "Knight";
                break;
            case '3':
                promotedPiece = "Rook";
                break;
            case '4':
                promotedPiece = "Bishop";
            default:
                break;
        }

        promotedPiece += "!";

        String menuString = "\n┌────────────────────────┐\n" +
                "│        Promotion       │\n" +
                "├────────────────────────┤\n" +
                "│ " + playerColor + " has promoted     │\n" +
                "│       a Pawn to a      │\n" +
                "" + parseString(promotedPiece, 24, true) + "\n" +
                "└────────────────────────┘\n";
        printMessage(menuString);
    }

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

    public final void printMenu() {
        String menuString = "\n┌────────────────────────┐\n" +
                "│       ChessMateAI      │\n" +
                "├────────────────────────┤\n" +
                "│        Main Menu       │\n" +
                "├────────────────────────┤\n" +
                "│ (1)      Play Chess    │\n" +
                "│                        │\n" +
                "│ (0)      Exit          │\n" +
                "└────────────────────────┘\n";
        printMessage(menuString);
    }

    public void printPromotion() {
        String menuString = "\n┌────────────────────────┐\n" +
                "│        Promotion       │\n" +
                "├────────────────────────┤\n" +
                "│  Choose your officer!  │\n" +
                "├────────────────────────┤\n" +
                "│ (1)           Queen    │\n" +
                "│ (2)           Knight   │\n" +
                "│ (3)           Rook     │\n" +
                "│ (4)           Bishop   │\n" +
                "└────────────────────────┘\n";
        printMessage(menuString);
    }

    public void printHumanPlayer(Piece.PlayerColor playerColor) {
        String winString = "\n┌────────────────────────┐\n" +
                "│      Human Player      │\n" +
                "├────────────────────────┤\n" +
                "│     Human plays as     │\n" +
                "│         " + playerColor + "!         │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    public void printAIPlayer(Piece.PlayerColor aIColor) {
        String winString = "\n┌────────────────────────┐\n" +
                "│        AI Player       │\n" +
                "├────────────────────────┤\n" +
                "│  ChessMateAI plays as  │\n" +
                "│         " + aIColor + "!         │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    public void printCheck(Piece.PlayerColor playerColor) {
        String winString = "\n┌────────────────────────┐\n" +
                "│          Check         │\n" +
                "├────────────────────────┤\n" +
                "│  " + playerColor + " King in check!  │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    public void printChoosePlayer() {
        String winString = "\n┌────────────────────────┐\n" +
                "│  Choose Human Player   │\n" +
                "├────────────────────────┤\n" +
                "│ (1)              WHITE │\n" +
                "│ (2)              BLACK │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    public void printIllegalAction(String msg) {
        String winString = "\n┌────────────────────────────────────────────┐\n" +
                "│               Illegal Action               │\n" +
                "├────────────────────────────────────────────┤\n" +
                "" + parseString(msg, 44, true) + "\n" +
                "└────────────────────────────────────────────┘\n";
        printMessage(winString);
    }

    public void printUnrecognizedCommand() {
        String winString = "\n┌────────────────────────┐\n" +
                "│          Oops!         │\n" +
                "├────────────────────────┤\n" +
                "│  Unrecognized command! │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

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

    public void printExit() {
        String winString = "┌────────────────────────┐\n" +
                "│          Exit          │\n" +
                "├────────────────────────┤\n" +
                "│          Bye!          │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }


    public final void printBoard(Board board) {
        printMessage(board.toString());
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

    private boolean isValidRank(char ch) {
        return Character.isDigit(ch) || (Character.getNumericValue(ch) < 1 || Character.getNumericValue(ch) > 8);
    }

    private boolean isValidFile(char ch) {
        return ch == 'a' || ch == 'b' || ch == 'c' || ch == 'd' || ch == 'e' || ch == 'f' || ch == 'g' || ch == 'h';
    }

    private String parseString(String text, int lineLength, boolean withStartPipe) {
        StringBuilder sb = new StringBuilder();

        if (text.length() > lineLength)
            return text.substring(0, lineLength);

        int textLength = text.length();
        lineLength -= textLength;

        if (withStartPipe)
            sb.append("│");

        for (int i = 0; i < lineLength; i++) {
            if (i == lineLength / 2)
                sb.append(text);
            sb.append(" ");
        }

        sb.append("│");
        return sb.toString();
    }

    private char getNumericCharForPromotion(String arrowMsg) {
        char ch = 'X';
        do {
            printArrow(arrowMsg);
            try {
                String str = scanner.nextLine();
                if (str.length() > 1)
                    continue;
                ch = str.charAt(0);
            } catch (Exception ignored) {

            }
        } while (!Character.isDigit(ch) || (Character.getNumericValue(ch) < 1 || Character.getNumericValue(ch) > 4));
        return ch;
    }

}