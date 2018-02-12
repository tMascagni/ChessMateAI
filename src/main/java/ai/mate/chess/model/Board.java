package ai.mate.chess.model;

import ai.mate.chess.handler.TextHandler;

public final class Board {

    private String[][] pos = {
            {TextHandler.BLACK_ROOK, TextHandler.BLACK_KNIGHT, TextHandler.BLACK_BISHOP, TextHandler.BLACK_QUEEN, TextHandler.BLACK_KING, TextHandler.BLACK_BISHOP, TextHandler.BLACK_KNIGHT, TextHandler.BLACK_ROOK},
            {TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN, TextHandler.BLACK_PAWN},
            {TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY},
            {TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY},
            {TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY},
            {TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY, TextHandler.EMPTY},
            {TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN, TextHandler.WHITE_PAWN},
            {TextHandler.WHITE_ROOK, TextHandler.WHITE_KNIGHT, TextHandler.WHITE_BISHOP, TextHandler.WHITE_QUEEN, TextHandler.WHITE_KING, TextHandler.WHITE_BISHOP, TextHandler.WHITE_KNIGHT, TextHandler.WHITE_ROOK}
    };

    private final String[][] board = {
            {"       ", "       ", "      ", "      ", "      ", "      ", "      ", "      ", "              "},
            {"       ", "   A   ", "  B   ", "  C   ", "  D   ", "  E   ", "  F   ", "  G   ", "  H           "},
            {"       ", "       ", "      ", "      ", "      ", "      ", "      ", "      ", "              "},
            {"       ", "┌─────┬", "─────┬", "─────┬", "─────┬", "─────┬", "─────┬", "─────┬", "─────┐        "},
            {"   8   ", "│ " + pos[0][0] + " │", " " + pos[0][1] + " │", " " + pos[0][2] + " │", " " + pos[0][3] + " │", " " + pos[0][4] + " │", " " + pos[0][5] + " │", " " + pos[0][6] + " │", " " + pos[0][7] + " │   8    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   7   ", "│ " + pos[1][0] + " │", " " + pos[1][1] + " │", " " + pos[1][2] + " │", " " + pos[1][3] + " │", " " + pos[1][4] + " │", " " + pos[1][5] + " │", " " + pos[1][6] + " │", " " + pos[1][7] + " │   7    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   6   ", "│     │", "     │", "     │", "     │", "     │", "     │", "     │", "     │   6    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   5   ", "│     │", "     │", "     │", "     │", "     │", "     │", "     │", "     │   5    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   4   ", "│     │", "     │", "     │", "     │", "     │", "     │", "     │", "     │   4    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   3   ", "│     │", "     │", "     │", "     │", "     │", "     │", "     │", "     │   3    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   2   ", "│ " + pos[6][0] + " │", " " + pos[6][1] + " │", " " + pos[6][2] + " │", " " + pos[6][3] + " │", " " + pos[6][4] + " │", " " + pos[6][5] + " │", " " + pos[6][6] + " │", " " + pos[6][7] + " │   2    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤      "},
            {"   1   ", "│ " + pos[7][0] + " │", " " + pos[7][1] + " │", " " + pos[7][2] + " │", " " + pos[7][3] + " │", " " + pos[7][4] + " │", " " + pos[7][5] + " │", " " + pos[7][6] + " │", " " + pos[7][7] + " │   1    "},
            {"       ", "└─────┴", "─────┴", "─────┴", "─────┴", "─────┴", "─────┴", "─────┴", "─────┘        "},
            {"       ", "       ", "      ", "      ", "      ", "      ", "      ", "      ", "              "},
            {"       ", "   A   ", "  B   ", "  C   ", "  D   ", "  E   ", "  F   ", "  G   ", "  H           "},
            {"       ", "       ", "      ", "      ", "      ", "      ", "      ", "      ", "              "}};


    private static Board instance;

    static {
        try {
            instance = new Board();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton Board instance!");
        }
    }

    private Board() {

    }

    public static synchronized Board getInstance() {
        return instance;
    }

    public final String[][] getPositionMatrix() {
        return pos;
    }

    public final String[][] getBoardMatrix() {
        return board;
    }

}