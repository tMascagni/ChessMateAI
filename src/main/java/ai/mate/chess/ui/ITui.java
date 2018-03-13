package ai.mate.chess.ui;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.IPiece;

public interface ITui {
    char getUserInput();
    BoardPosition getBoardPositionInput();
    void printPressEnter();
    void printArrow();
    void printArrow(String postfix);
    void printStartScreen();
    void printMenu();
    void printHumanPlayer(IPiece.Color playerColor);
    void printChoosePlayer();
    IPiece.Color getPlayerColorInput();
    void printUnrecognizedCommand();
    void printWin();
    void printLoss();
    void printExit();
    void printBoard(Board board);
}