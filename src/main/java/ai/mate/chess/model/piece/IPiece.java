package ai.mate.chess.model.piece;

/*
 * Interface for the general chess piece.
 */
public interface IPiece {

    String getName();
    Color getColor();
    //List<Point> getPossibleMoves();

    enum Color {
        WHITE, BLACK
    }

}