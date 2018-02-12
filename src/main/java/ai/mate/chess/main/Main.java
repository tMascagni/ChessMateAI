package ai.mate.chess.main;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.piece.IPiece;
import ai.mate.chess.model.piece.Pawn;
import ai.mate.chess.tui.Tui;

public class Main {

    public static void main(String[] args) {
        IPiece pawn = new Pawn(IPiece.Color.BLACK);
        Board board = Board.getInstance();
        Tui tui = Tui.getInstance();

        System.out.println(pawn);
        tui.printBoard(board);
    }

}