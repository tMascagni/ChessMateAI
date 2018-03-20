package ai.mate.chess.ui;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.IPiece;
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
        char file = getAlphabeticChar("File");
        char rank = getNumericChar("Rank");
        return new BoardPosition(file, rank);
    }

    @Override
    public void printPressEnter() {
        printArrow();
        printMessage("Press Enter...");
        scanner.nextLine();
    }

    @Override
    public void printArrow() {
        printMessage(Utils.ARROW + " ");
    }

    @Override
    public void printPromotionSuccess(char selection, IPiece.Color playerColor) {
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

        String menuString = "\n┌────────────────────────┐\n" +
                "│        Promotion       │\n" +
                "├────────────────────────┤\n" +
                "│ " + playerColor + " has   │\n" +
                "│ promoted a Pawn to a   │\n" +
                "│ " + promotedPiece + "!      │\n" +
                "└────────────────────────┘\n";
        printMessage(menuString);
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

    @Override
    public void printHumanPlayer(IPiece.Color playerColor) {
        String winString = "\n┌────────────────────────┐\n" +
                "│      Human Player      │\n" +
                "├────────────────────────┤\n" +
                "│         " + playerColor + "          │\n" +
                "│  is the human player!  │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    @Override
    public void printChoosePlayer() {
        String winString = "\n┌────────────────────────┐\n" +
                "│  Choose Human Player   │\n" +
                "├────────────────────────┤\n" +
                "│ (1)              WHITE │\n" +
                "│ (2)              BLACK │\n" +
                "└────────────────────────┘\n";
        printMessage(winString);
    }

    @Override
    public void printIllegalAction(String msg) {
        String winString = "\n┌────────────────────────┐\n" +
                "│     Illegal Action     │\n" +
                "├────────────────────────┤\n" +
                "│       " + msg + "     │\n" +
                "└────────────────────────┘\n\n";
        printMessage(winString);
    }

    @Override
    public IPiece.Color getPlayerColorInput() {
        char input;
        do {
            input = getUserInput();
        } while (input != '1' && input != '2');

        if (input == '1')
            return IPiece.Color.WHITE;
        else
            return IPiece.Color.BLACK;
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
    public void printExit() {
        String winString = "┌────────────────────────┐\n" +
                "│          Exit          │\n" +
                "├────────────────────────┤\n" +
                "│          Bye!          │\n" +
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

    private char getAlphabeticChar(String arrowMsg) {
        char ch;
        while (true) {
            printArrow(arrowMsg);
            try {
                ch = scanner.nextLine().charAt(0);
            } catch (Exception e) {
                continue;
            }

            if (!Character.isAlphabetic(ch))
                continue;

            ch = Character.toLowerCase(ch);

            if (ch == 'a' || ch == 'b' || ch == 'c' || ch == 'd' || ch == 'e' || ch == 'f' || ch == 'g' || ch == 'h')
                return ch;
        }
    }

    private char getNumericChar(String arrowMsg) {
        char ch = 'X';

        do {
            printArrow(arrowMsg);
            try {
                String str = scanner.nextLine();

                if (str.length() > 1)
                    continue;

                ch = str.charAt(0);

            } catch (Exception e) {
                continue;
            }
        } while (!Character.isDigit(ch) || (Character.getNumericValue(ch) < 1 || Character.getNumericValue(ch) > 8));
        return ch;
    }

    @Override
    public char getPromotionSelection() {
        return getNumericCharForPromotion("Selection");
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

            } catch (Exception e) {
                continue;
            }
        } while (!Character.isDigit(ch) || (Character.getNumericValue(ch) < 1 || Character.getNumericValue(ch) > 4));
        return ch;
    }

}