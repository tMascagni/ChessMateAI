package ai.mate.chess.main;

import ai.mate.chess.model.piece.IPiece;
import ai.mate.chess.model.piece.Pawn;

public class Main {

    public static void main(String[] args) {
        IPiece pawn = new Pawn(IPiece.Color.BLACK);
        System.out.println(pawn);
    }

}