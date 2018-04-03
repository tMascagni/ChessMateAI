package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;

import java.awt.*;
import java.util.List;

/*
 * Konge
 */
public final class King extends Piece {

    public King(PlayerColor color, Point position) {
        super(color, position);
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][] {
                { -30,-40,-40,-50,-50,-40,-40,-30 },
                { -30,-40,-40,-50,-50,-40,-40,-30 },
                { -30,-40,-40,-50,-50,-40,-40,-30 },
                { -30,-40,-40,-50,-50,-40,-40,-30 },
                { -20,-30,-30,-40,-40,-30,-30,-20 },
                { -10,-20,-20,-20,-20,-20,-20,-10 },
                {  20, 20,  0,  0,  0,  0, 20, 20 },
                {  20, 30, 10,  0,  0, 10, 30, 20 }
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[] {true, true, true, true, true, true, true, true};
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        return null;
    }

    @Override
    public Piece copy() {
        return null;
    }

}