package ai.mate.chess.utils;

import ai.mate.chess.model.piece.Piece;

public final class ChessUtils {

    /* Black Pieces */
    private static final String BLACK_ID = "*";

    public static final String BLACK_ROOK = BLACK_ID + "R" + BLACK_ID;
    public static final String BLACK_KNIGHT = BLACK_ID + "N" + BLACK_ID;
    public static final String BLACK_BISHOP = BLACK_ID + "B" + BLACK_ID;
    public static final String BLACK_QUEEN = BLACK_ID + "Q" + BLACK_ID;
    public static final String BLACK_KING = BLACK_ID + "K" + BLACK_ID;
    public static final String BLACK_PAWN = BLACK_ID + "P" + BLACK_ID;

    /* White Pieces */
    private static final String WHITE_ID = " ";

    public static final String WHITE_ROOK = WHITE_ID + "R" + WHITE_ID;
    public static final String WHITE_KNIGHT = WHITE_ID + "N" + WHITE_ID;
    public static final String WHITE_BISHOP = WHITE_ID + "B" + WHITE_ID;
    public static final String WHITE_QUEEN = WHITE_ID + "Q" + WHITE_ID;
    public static final String WHITE_KING = WHITE_ID + "K" + WHITE_ID;
    public static final String WHITE_PAWN = WHITE_ID + "P" + WHITE_ID;

    public static final String ERROR_NAME = "ERROR";

    /* Piece scores */
    public static final int BISHOP_SCORE = 300;
    public static final int ROOK_SCORE = 500;
    public static final int QUEEN_SCORE = 900;
    public static final int PAWN_SCORE = 100;
    public static final int KNIGHT_SCORE = 300;
    public static final int KING_SCORE = 10000;

    private static ChessUtils instance;

    static {
        try {
            instance = new ChessUtils();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton ChessUtils instance!");
        }
    }

    private ChessUtils() {

    }

    public static synchronized ChessUtils getInstance() {
        return instance;
    }

    public static Piece.PlayerColor changePlayer(Piece.PlayerColor playerColor) {
        return playerColor == Piece.PlayerColor.WHITE ? Piece.PlayerColor.BLACK : Piece.PlayerColor.WHITE;
    }

}