package ai.mate.chess.model.move;

import java.util.Stack;

public final class MoveHistory {

    private final Stack<Move> moveStack = new Stack<>();

    private final static MoveHistory instance;

    static {
        try {
            instance = new MoveHistory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Singleton MoveHistory instance!");
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

    public int getHistorySize() {
        return moveStack.size();
    }

}