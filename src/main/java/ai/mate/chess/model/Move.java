package ai.mate.chess.model;

import ai.mate.chess.model.piece.IPiece;

public class Move {

    private BoardPosition fromPos;
    private BoardPosition toPos;
    private IPiece.Color fromColor;
    private IPiece.Color toColor;
    private boolean toPieceWasSlain; /* get most recent from toColor loss List */

    public Move(BoardPosition fromPos, BoardPosition toPos, IPiece.Color fromColor, IPiece.Color toColor, boolean toPieceWasSlain) {
        this.fromPos = fromPos;
        this.toPos = toPos;
        this.fromColor = fromColor;
        this.toColor = toColor;
        this.toPieceWasSlain = toPieceWasSlain;
    }

    public BoardPosition getFromPos() {
        return fromPos;
    }

    public BoardPosition getToPos() {
        return toPos;
    }

    public IPiece.Color getFromColor() {
        return fromColor;
    }

    public IPiece.Color getToColor() {
        return toColor;
    }

    public boolean isToPieceWasSlain() {
        return toPieceWasSlain;
    }

}