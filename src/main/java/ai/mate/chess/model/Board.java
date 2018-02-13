package ai.mate.chess.model;

import ai.mate.chess.model.piece.*;

public final class Board {

    private IPiece[][] pos = {
            {new Rook(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Queen(IPiece.Color.BLACK), new King(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Rook(IPiece.Color.BLACK)},
            {new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK)},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE)},
            {new Rook(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Queen(IPiece.Color.WHITE), new King(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Rook(IPiece.Color.WHITE)}
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
            {"   6   ", "│ " + pos[2][0] + " │", " " + pos[2][1] + " │", " " + pos[2][2] + " │", " " + pos[2][3] + " │", " " + pos[2][4] + " │", " " + pos[2][5] + " │", " " + pos[2][6] + " │", " " + pos[2][7] + " │   6    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   5   ", "│ " + pos[3][0] + " │", " " + pos[3][1] + " │", " " + pos[3][2] + " │", " " + pos[3][3] + " │", " " + pos[3][4] + " │", " " + pos[3][5] + " │", " " + pos[3][6] + " │", " " + pos[3][7] + " │   5    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   4   ", "│ " + pos[4][0] + " │", " " + pos[4][1] + " │", " " + pos[4][2] + " │", " " + pos[4][3] + " │", " " + pos[4][4] + " │", " " + pos[4][5] + " │", " " + pos[4][6] + " │", " " + pos[4][7] + " │   4    "},
            {"       ", "├─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┼", "─────┤        "},
            {"   3   ", "│ " + pos[5][0] + " │", " " + pos[5][1] + " │", " " + pos[5][2] + " │", " " + pos[5][3] + " │", " " + pos[5][4] + " │", " " + pos[5][5] + " │", " " + pos[5][6] + " │", " " + pos[5][7] + " │   3    "},
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

    public final IPiece[][] getPositionMatrix() {
        return pos;
    }

    public final String[][] getBoardMatrix() {
        return board;
    }

}