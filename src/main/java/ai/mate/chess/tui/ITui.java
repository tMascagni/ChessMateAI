package ai.mate.chess.tui;

import ai.mate.chess.model.Board;

public interface ITui {
    void printStartScreen();
    void printMenu();
    void printWin();
    void printLoss();
    void printBoard(Board board);
}