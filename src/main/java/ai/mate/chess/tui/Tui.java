package ai.mate.chess.tui;

import ai.mate.chess.model.Board;

public final class Tui {

    private static Tui instance;

    static {
        try {
            instance = new Tui();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton Tui instance!");
        }
    }

    private Tui() {

    }

    public static synchronized Tui getInstance() {
        return instance;
    }

    public final void printBoard(Board board) {
        StringBuilder sb = new StringBuilder();
        String[][] matrix = board.getBoardMatrix();
        int colSize = matrix[0].length;

        for (String[] rowVector : matrix) {
            for (int j = 0; j < colSize; j++)
                sb.append(rowVector[j]);
            sb.append("\n");
        }

        printMessage(sb.toString());
    }

    private void printMessage(String msg) {
        System.out.print(msg);
    }

}