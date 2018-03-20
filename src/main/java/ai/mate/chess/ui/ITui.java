package ai.mate.chess.ui;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.IPiece;

public interface ITui {
    char getUserInput();
    char getPromotionSelection();
    BoardPosition getBoardPositionInput();
    void printPressEnter();
    void printArrow();
    void printPromotionSuccess(char selection, IPiece.Color playerColor);
    void printArrow(String postfix);
    void printStartScreen();
    void printMenu();
    void printPromotion();
    void printHumanPlayer(IPiece.Color playerColor);
    void printChoosePlayer();
    void printIllegalAction(String msg);
    IPiece.Color getPlayerColorInput();
    void printUnrecognizedCommand();
    void printWin();
    void printLoss();
    void printExit();
    void printBoard(Board board);
}