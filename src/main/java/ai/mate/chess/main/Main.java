package ai.mate.chess.main;

import ai.mate.chess.model.Board;
import ai.mate.chess.tui.Tui;

public class Main {

    public static void main(String[] args) {
        Board board = Board.getInstance();
        Tui tui = Tui.getInstance();

        tui.printBoard(board);

    }

}