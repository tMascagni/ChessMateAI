package ai.mate.chess.model.move;

import java.util.Stack;

public final class MoveHistory {

    private Stack<Move> moveStack = new Stack<>();

    private static MoveHistory instance;

    static {
        try {
            instance = new MoveHistory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton MoveHistory instance!");
        }
    }

    private MoveHistory() {

    }

    public static synchronized MoveHistory getInstance() {
        return instance;
    }

    public void addMove(Move move) {
        moveStack.add(move);
    }

    public Move popMove() {
        return moveStack.pop();
    }

}