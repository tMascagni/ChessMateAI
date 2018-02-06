package ai.mate.chess.model.brick;

/*
 * This class is the super class for all the chess pieces.
 */
public abstract class Piece {

    /*
     * Textual representation of the brick.
     */
    public String pieceName = "";

    /*
     * Move counter for a brick
     */
    public int moveCount = 0;


    /*
     * Enum for the Piece color.
     */
    public enum Color {
        WHITE, BLACK
    }

    /*
     * Returns whether this piece
     * is able to be moved or not.
     */
    public boolean isMovable;




}