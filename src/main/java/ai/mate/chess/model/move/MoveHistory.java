package ai.mate.chess.model.move;

import java.util.Stack;

/**
 * Singleton object which keeps track of the updatePiece history
 */

public final class MoveHistory {

    private static MoveHistory instance = null;
    private Stack<Move> moveList = new Stack<>();

    public static MoveHistory getInstance() {
        if (instance == null) {
            instance = new MoveHistory();
        }

        return instance;
    }

    public void addMove(Move move) {
        moveList.add(move);
    }

    public Move popLastMove() {
        return moveList.pop();
    }
}
